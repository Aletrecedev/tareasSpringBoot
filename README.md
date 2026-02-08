# CRUD MVC con Thymeleaf — RA3

## 1) Datos del alumno/a
- Nombre y apellidos: Alejandro Duré
- Curso / Grupo: 2º DAW
- Entidad elegida: Producto

## 2) Repositorio (fork) y gestión de versiones
- Repositorio base: https://github.com/profeInformatica101/tareasSpringBoot
- Enlace a MI fork: https://github.com/Aletrecedev/tareasSpringBoot
- Nº de commits realizados: 6 (incluyendo este README)

## 3) Arquitectura
Explica brevemente cómo has organizado:
- **Controller:** `ProductoController`: Gestiona las peticiones HTTP, conecta el servicio con las vistas HTML y define las rutas de navegación.
- **Service:** `ProductoService`: Contiene la lógica de negocio y hace de puente entre el controlador y el repositorio.
- **Repository:** `ProductoRepository`: Interfaz que extiende de `JpaRepository` para realizar operaciones CRUD automáticas con la base de datos.
- **Entity:** `Producto`: Clase Java anotada con `@Entity` que representa la tabla de productos en la base de datos.

## 4) Base de datos elegida (marca una)
- [x] H2 (Base de datos en memoria)
- [ ] MySQL
- [ ] PostgreSQL

## 5) Configuración de la base de datos

### 5.1 Dependencias añadidas
He utilizado la dependencia de H2 Database:
`com.h2database:h2`

### 5.2 application.properties
(Configuración por defecto de Spring Boot para H2 en memoria).
Para habilitar la consola de H2 se pueden usar estas líneas:
spring.h2.console.enabled=true
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

### 5.3 Pasos para crear la BD
- Al usar H2 en memoria con `spring.jpa.hibernate.ddl-auto=update` (por defecto), la base de datos y las tablas se crean automáticamente al iniciar la aplicación.

## 6) Cómo ejecutar el proyecto
1. Requisitos: Tener Java 17/21 instalado y Maven.
2. Comando:
   - Ejecutar la clase `CrudSpringApplication`.
   - Desde terminal: `./mvnw spring-boot:run`
3. URL de acceso:
   - http://localhost:8080/productos

## 7) Pantallas / Rutas MVC
- GET /productos (Listar todos los productos)
- GET /productos/nuevo (Formulario de alta)
- POST /productos (Guardar producto nuevo o editado)
- GET /productos/editar/{id} (Formulario de edición)
- GET /productos/eliminar/{id} (Eliminar producto)

## 8) Mejoras extra (opcional)
- Estilos con Bootstrap 4 (CDN añadido en las vistas).
- Botón de confirmación antes de eliminar.