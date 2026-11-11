# Red Emprendedora - Backend

API REST para gestionar ferias y emprendedores: eventos, stands, usuarios y localidades.

## Requisitos

- Java 21
- PostgreSQL 18
- Maven (incluido con `mvnw`)

## Configuración

1. Creá una base de datos en PostgreSQL (por ejemplo `red_emprendedora_db`).
2. Copiá `.env.example` como `.env` en la raíz del proyecto y completá tus datos:

   ```
   DB_HOST=localhost
   DB_PORT=5432
   DB_NAME=red_emprendedora_db
   DB_USERNAME=postgres
   DB_PASSWORD=tu_contraseña

   JWT_SECRET=una_clave_inventada_de_al_menos_32_caracteres
   JWT_EXPIRATION_MS=86400000
   ```

   - `JWT_SECRET` es obligatoria y tiene que tener **al menos 32 caracteres**; si no, la aplicación no arranca.
   - `JWT_EXPIRATION_MS` es la duración del token en milisegundos (86400000 = 24 horas). Si no se completa, se usan 24 horas.
   - El archivo `.env` está en `.gitignore`: nunca se sube a Git.
3. Cargá los roles en tu base local:

   ```sql
   INSERT INTO usuario_rol (nombre) VALUES ('ORGANIZADOR'), ('EMPRENDEDOR');
   ```

   Los usuarios se relacionan con sus roles en la tabla `usuario_x_rol`, que Hibernate crea sola al arrancar.

## Cómo ejecutar

```
./mvnw spring-boot:run
```

La API queda en `http://localhost:8080`. En IntelliJ, el Working directory de la configuración de ejecución tiene que ser la carpeta del proyecto, para que lea el `.env`.

## Autenticación (JWT)

1. **Registrarse** (público): `POST /api/usuarios`. Un usuario puede ser ORGANIZADOR, EMPRENDEDOR o las dos cosas. Los roles y la localidad son obligatorios.

   ```json
   {
     "nombre": "Ana Pérez",
     "email": "ana@mail.com",
     "telefono": "1122334455",
     "direccion": "Calle 123",
     "contrasena": "123456",
     "nombreEmprendimiento": "Velas Ana",
     "tipoEmprendimiento": "Decoración",
     "instagram": "@velasana",
     "roles": ["EMPRENDEDOR", "ORGANIZADOR"],
     "idLocalidad": 1
   }
   ```

2. **Iniciar sesión** (público): `POST /api/auth/login`

   ```json
   { "email": "ana@mail.com", "contrasena": "123456" }
   ```

   Respuesta:

   ```json
   {
     "token": "eyJhbGciOi...",
     "tipo": "Bearer",
     "expiraEnMs": 86400000,
     "idUsuario": 1,
     "nombre": "Ana Pérez",
     "email": "ana@mail.com",
     "roles": ["EMPRENDEDOR", "ORGANIZADOR"]
   }
   ```

3. En el resto de los pedidos que requieren login se manda el header:

   ```
   Authorization: Bearer <token>
   ```

## Permisos

| Acción | Quién puede |
|---|---|
| Ver eventos, stands y localidades (GET) | Cualquiera, sin login |
| Registrarse e iniciar sesión | Cualquiera, sin login |
| Crear, editar y eliminar eventos, stands y localidades | ORGANIZADOR |
| Reservar y cancelar reservas de stands | EMPRENDEDOR u ORGANIZADOR |
| Ver usuarios | Cualquier usuario logueado |

Además:

- Solo el organizador dueño puede editar o eliminar su evento y los stands de su evento.
- Una reserva la puede cancelar quien la hizo o el organizador del evento.
- Cada usuario solo puede editar o eliminar su propia cuenta.
- El organizador de un evento y el usuario que reserva un stand se toman del token, no se mandan en el body.

## Endpoints principales

| Recurso | Ruta |
|---|---|
| Autenticación | `/api/auth/login` |
| Usuarios | `/api/usuarios` |
| Eventos | `/api/eventos` (filtros opcionales: `?estado=&idLocalidad=&idUsuario=`) |
| Stands | `/api/stands` (paginado; filtros opcionales: `?idEvento=&estadoStand=&numeroStand=&nombreEvento=&idUsuario=`) |
| Localidades | `/api/localidades` |

### Crear un evento

Requiere token de un usuario con rol ORGANIZADOR. El organizador es el usuario logueado:

```json
{
  "nombre": "Feria X",
  "fechaInicio": "2026-12-10",
  "fechaFin": "2026-12-12",
  "precioEntrada": 0,
  "metrosCuadrados": "300",
  "idLocalidad": 1
}
```

El evento arranca en estado "Activo". Si se elimina un evento con stands reservados, no se borra: pasa a "Cancelado".

### Reserva de stands

- Reservar: `PUT /api/stands/{id}/reservar` (la reserva queda a nombre del usuario logueado)
- Cancelar reserva: `PUT /api/stands/{id}/cancelar-reserva`

Al reservar el último stand disponible, el evento pasa a "Agotado". Al cancelar una reserva, vuelve a "Activo".

## Errores

Todas las respuestas de error tienen el mismo formato:

```json
{
  "timestamp": "2026-10-25T11:20:00",
  "status": 400,
  "mensaje": "Descripción del error"
}
```

Cuando falla la validación de un campo, además viene `errores` con el mensaje de cada campo:

```json
{
  "timestamp": "2026-10-25T11:20:00",
  "status": 400,
  "mensaje": "Hay campos con datos inválidos",
  "errores": { "roles": "Tenés que elegir al menos un rol" }
}
```

| Código | Cuándo |
|---|---|
| 400 | Datos inválidos o regla de negocio no cumplida |
| 401 | Falta el token, es inválido o venció; o email/contraseña incorrectos en el login |
| 403 | El usuario no tiene permiso para esa acción |
| 404 | El recurso no existe |
| 409 | Se viola una restricción de la base (por ejemplo, borrar algo que está en uso) |
