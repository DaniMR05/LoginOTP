=== Descripción del proyecto ===
Autenticación con credenciales (email + contraseña) seguida por verificación OTP vía correo como segundo factor (MFA). El registro (sign-up) solo crea la cuenta; el OTP se solicita únicamente al iniciar sesión (sign-in).
Backend: Spring Boot (Java 21, JPA, PostgreSQL, Mail) en puerto 8080.
Frontend: React + Vite en puerto 5173.
El backend expone /auth/signup, /auth/signin y /otp/verify. El frontend consume estos endpoints con axios.

=== Instrucciones para ejecutar el sistema ===

Requisitos: Java 21, Maven 3.9+, PostgreSQL 12+, Node.js 20.19+ y npm.

Base de datos: crea la base y tablas en PostgreSQL (ej.: DB “mfa”; tablas user_account y otp_code si persistes los OTP).

Configura backend: en application.properties define la URL de PostgreSQL y credenciales; configura correo SMTP de Gmail con App Password (16 caracteres sin espacios).

Levanta backend:

./mvnw spring-boot:run (o mvn spring-boot:run)

Servirá en http://localhost:8080

Levanta frontend:

npm install

npm run dev

Abre http://localhost:5173

Verifica CORS: el backend debe permitir origen http://localhost:5173
.

=== Ejemplo de flujo de login + OTP ===

Registrar usuario (una vez):

POST http://localhost:8080/auth/signup

Body JSON: { "firstName":"Luis", "lastName":"Sosa", "email":"luis@test.com", "password":"123456" }

Iniciar sesión (dispara envío de OTP por correo):

POST http://localhost:8080/auth/signin

Body JSON: { "email":"luis@test.com", "password":"123456" }
Respuesta esperada: “OTP sent”.

Verificar OTP:

POST http://localhost:8080/otp/verify

Body JSON: { "email":"luis@test.com", "code":123456 }
Respuesta esperada: “OTP verified successfully”.
