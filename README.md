# Sample Spring Boot App

## Running the Application

### Bash
```
./gradlew bootJar
chmod u+x build/libs/FutureStay-0.0.1-SNAPSHOT.jar
./build/libs/FutureStay-0.0.1-SNAPSHOT.jar 
```
The application will be accessible at `http://localhost:8080`

## Endpoints of Interest

### GET /api/swagger-ui/index.html
Swagger UI

### GET /api/v3/api-docs
OpenAPI 3.0.1 Definition (JSON)

### GET /api/v3/api-docs.yaml
OpenAPI 3.0.1 Definition (YAML)

### POST /api/bookings
Create a booking for a property. See one of the API specs for payload details
