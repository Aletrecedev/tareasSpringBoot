package com.alejandro.crudspring.config;

import org.springframework.boot.security.autoconfigure.web.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    // 1. AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // 2. PasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 3. Usuarios en Memoria
    @Bean
    public UserDetailsService users(PasswordEncoder passwordEncoder) {
        UserDetails user1 = User.builder()
                .username("user1")
                .password(passwordEncoder.encode("user1")) // Contraseña "user1"
                .roles("USER")
                .build();

        UserDetails admin1 = User.builder()
                .username("admin1")
                .password(passwordEncoder.encode("admin1")) // Contraseña "admin1"
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user1, admin1);
    }

    // 4. Filtro de Seguridad
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // Configuración para permitir la consola H2
        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        http.authorizeHttpRequests(auth -> auth
                // Recursos estáticos
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()

                // Páginas públicas
                .requestMatchers("/", "/saluda").permitAll()

                // Consola H2: Solo ADMIN
                .requestMatchers(PathRequest.toH2Console()).hasRole("ADMIN")

                // Productos: USER o ADMIN
                .requestMatchers("/productos/**").hasAnyRole("USER", "ADMIN")

                .anyRequest().authenticated()
        );


        http.csrf(csrf -> csrf
                .ignoringRequestMatchers(PathRequest.toH2Console())
                .ignoringRequestMatchers("/h2-console/**")
        );

        // Formulario de Login
        http.formLogin(form -> form
                .defaultSuccessUrl("/productos", true)
                .permitAll()
        );

        // Configuración de Logout
        http.logout(logout -> logout.permitAll());

        return http.build();
    }
}