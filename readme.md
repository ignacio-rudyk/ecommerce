# 🛒 Generic Ecommerce

API REST de un ecommerce desarrollada con Java y Spring Boot, orientada a demostrar buenas prácticas de desarrollo backend. Proyecto de práctica para portfolio.

---

## 🧰 Tecnologías utilizadas

- Java 17
- Spring Boot 3.5.9
- Spring Data JPA / Hibernate 6.6
- PostgreSQL 16
- HikariCP
- Maven

---

## 📐 Arquitectura

El proyecto sigue una arquitectura en capas:

- **Controller** — recibe las requests HTTP y delega al service
- **Service** — contiene la lógica de negocio y lanza excepciones personalizadas
- **Repository** — acceso a la base de datos mediante Spring Data JPA
- **DTO** — objetos de transferencia de datos para requests y responses
- **Exception** — manejo centralizado de errores con `GlobalExceptionHandler`

---

## 📦 Estructura del proyecto
```
src/main/java/com/ignacio/rudyk/generic/ecommerce/
├── controller/
├── dto/
│   └── response/
├── enumerate/
├── exception/
├── repository/
│   └── entity/
└── service/
    └── implementation/
```

---

## 🗄️ Modelo de datos

El proyecto incluye las siguientes entidades:

- **User** — usuarios con estado, contacto y contraseña
- **Role** — roles de usuario
- **UserState** — estados posibles de un usuario
- **UserContact** — email y teléfono del usuario
- **Password** — contraseña encriptada con salt
- **Product** — productos con categoría y precio
- **Category** — categorías de productos
- **Cart** — carrito de compras del usuario
- **CartProduct** — productos dentro de un carrito
- **Orders** — órdenes de compra
- **OrderState** — estados posibles de una orden
- **Payment** — pagos realizados
- **PaymentState** — estados posibles de un pago
- **PaymentMethod** — métodos de pago disponibles

---

## ⚙️ Configuración y setup

### Requisitos previos

- Java 17
- Maven 3.9+
- PostgreSQL 16

### 1. Clonar el repositorio
```bash
git clone https://github.com/ignacio-rudyk/generic-ecommerce.git
cd generic-ecommerce
```

### 2. Crear la base de datos y el usuario en PostgreSQL

Ejecutar los siguientes comandos en psql como superusuario:
```sql
CREATE DATABASE generic_ecommerce;
CREATE USER generic_ecommerce_user WITH PASSWORD 'tu_password';
GRANT ALL PRIVILEGES ON DATABASE generic_ecommerce TO generic_ecommerce_user;

\c generic_ecommerce

GRANT ALL ON SCHEMA public TO generic_ecommerce_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO generic_ecommerce_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON SEQUENCES TO generic_ecommerce_user;
```

### 3. Configurar application.properties

Editar el archivo `src/main/resources/application.properties`:
```properties
spring.application.name=ecommerce

spring.datasource.url=jdbc:postgresql://localhost:5432/generic_ecommerce
spring.datasource.username=generic_ecommerce_user
spring.datasource.password=tu_password

spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
```

> ⚠️ Si tenés variables de entorno definidas en el sistema (`SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`), estas tienen mayor prioridad que el `application.properties` y pueden pisar la configuración. Verificá que no estén definidas antes de correr la aplicación.

### 4. Compilar y ejecutar
```bash
mvn clean install
mvn spring-boot:run
```

La aplicación levanta en `http://localhost:8080`.

---

## 🔁 Endpoints disponibles

### Usuario

| Método | Endpoint | Descripción                  |
|--------|----------|------------------------------|
| POST | `/user/create-user` | Crear un nuevo usuario       |
| GET | `/user/{id}` | Obtener un usuario por ID    |
| PUT | `/user/{id}` | Actualizar un usuario por ID |
| DELETE | `/user/{id}` | Eliminar un usuario por ID   |

---

## 👤 Autor

**Ignacio Nahuel Rudyk**  
[LinkedIn](https://www.linkedin.com/in/ignacio-rudyk) · [GitHub](https://github.com/ignacio-rudyk)