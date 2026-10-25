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
   ```

   El archivo `.env` está en `.gitignore`: nunca se sube a Git.
3. Cargá los roles en tu base local:

   ```sql
   INSERT INTO usuario_rol (nombre) VALUES ('ORGANIZADOR'), ('EMPRENDEDOR');
   ```

   Los ids pueden variar en cada base. Consultalos con `SELECT * FROM usuario_rol;`.

## Cómo ejecutar

```
./mvnw spring-boot:run
```

La API queda en `http://localhost:8080`. En IntelliJ, el Working directory de la configuración de ejecución tiene que ser la carpeta del proyecto, para que lea el `.env`.

## Endpoints principales

| Recurso | Ruta |
|---|---|
| Eventos | `/api/eventos` |
| Stands | `/api/stands` |
| Usuarios | `/api/usuarios` |
| Localidades | `/api/localidades` |

### Reserva de stands

- Reservar: `PUT /api/stands/{id}/reservar?idUsuario=5`
- Cancelar reserva: `PUT /api/stands/{id}/cancelar-reserva`

Al reservar el último stand disponible, el evento pasa a "Agotado". Al cancelar una reserva, vuelve a "Activo".

### Crear un evento

Hace falta una localidad y un usuario con rol ORGANIZADOR:

```json
{
  "nombre": "Feria X",
  "fechaInicio": "2026-12-10",
  "fechaFin": "2026-12-12",
  "metrosCuadrados": "300",
  "idLocalidad": 1,
  "idUsuario": 1
}
```

## Errores

Todas las respuestas de error tienen el mismo formato:

```json
{
  "timestamp": "2026-10-25T11:20:00",
  "status": 400,
  "mensaje": "Descripción del error"
}
```