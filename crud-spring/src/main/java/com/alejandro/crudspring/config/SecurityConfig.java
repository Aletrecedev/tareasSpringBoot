package com.alejandro.crudspring.config;

// --- IMPORTS DE SPRING SECURITY Y UTILS ---

import org.springframework.boot.security.autoconfigure.web.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    // 3. Filtro de Seguridad
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        //  Reglas de acceso (Autorización)
        http.authorizeHttpRequests(auth -> auth
                // Recursos estáticos
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()

                // Rutas públicas
                .requestMatchers("/", "/login", "/logout", "/error").permitAll()

                // Consola H2: Solo ADMIN
                .requestMatchers(PathRequest.toH2Console()).hasRole("ADMIN")

                // 1. VER PRODUCTOS: Lo permitimos a USUARIO y ADMIN
                .requestMatchers("/productos").hasAnyRole("USUARIO", "ADMIN")

                // 2. GESTIONAR PRODUCTOS Solo ADMIN
                .requestMatchers("/productos/**").hasRole("ADMIN")
                .anyRequest().authenticated()
        );

        //  CSRF (Desactivarlo SOLO para la consola H2)
        http.csrf(csrf -> csrf
                .ignoringRequestMatchers(PathRequest.toH2Console())
        );

        //  Formulario de Login
        http.formLogin(form -> form
                .defaultSuccessUrl("/productos", true) // Al entrar, va a productos
                .permitAll()
        );

        //  Logout
        http.logout(logout -> logout
                .permitAll()
        );

        return http.build();
    }
}