# Sistema Retail — Microservicios EV3

Sistema de gestión de retail desarrollado con arquitectura de microservicios usando Spring Boot y Spring Cloud Gateway.

---

## Descripcion del proyecto

Sistema de retail compuesto por dos microservicios independientes (MS_PRODUCTOS y MS_VENTAS) comunicados entre si mediante Feign Client, con un API Gateway centralizado que enruta las peticiones. La base de datos utilizada es Oracle Cloud (Autonomous Database). El sistema permite gestionar productos y registrar ventas con control de stock en tiempo real.

---

## Integrantes del equipo

| Nombre | Aporte |
|--------|--------|
| Antonio Suazo | Desarrollo del MS_PRODUCTOS, configuracion de la base de datos Oracle y configuracion del API Gateway |
| Felipe Cofre | Desarrollo del MS_VENTAS, implementacion del cliente Feign y validaciones de negocio |
| Felipe Rubilar | Implementacion de pruebas unitarias con Mockito, configuracion de Swagger/OpenAPI en ambos microservicios |
| Anastasia Orellana | Configuracion de Docker, documentacion del proyecto y gestion del repositorio Git |

---

## Estructura del proyecto


Ev3-Retail/   
├── EV_3_evRetail-master/    
├── ev3-ms-venta-master/     
├── api-gateway/              
├── docker-compose.yml   
└── README.md


---

## APIs y endpoints disponibles

### MS_PRODUCTOS (puerto 8082)

| Metodo | Endpoint | Descripcion |
|--------|----------|-------------|
| GET | /api/productos | Listar todos los productos |
| GET | /api/productos/{id} | Obtener producto por ID |
| POST | /api/productos | Crear nuevo producto |
| PUT | /api/productos/{id} | Actualizar producto |
| DELETE | /api/productos/{id} | Eliminar producto |
| PUT | /api/productos/{id}/reducir-stock/ | Reducir stock |

### MS_VENTAS (puerto 8083)

| Metodo | Endpoint | Descripcion |
|--------|----------|-------------|
| GET | /api/ventas | Listar todas las ventas |
| GET | /api/ventas/{id} | Obtener venta por ID |
| POST | /api/ventas | Crear nueva venta |
| PUT | /api/ventas/{id} | Actualizar venta |
| DELETE | /api/ventas/{id} | Eliminar venta |

---

## Servicios disponibles

| Servicio | URL |
|----------|-----|
| API Gateway | http://localhost:8080 |
| MS_PRODUCTOS | http://localhost:8082 |
| MS_VENTAS | http://localhost:8083 |

---

## Enlaces de Swagger

- MS_PRODUCTOS: http://localhost:8082/swagger-ui/index.html
- MS_VENTAS: http://localhost:8083/swagger-ui/index.html
- Api Gateway: http://localhost:8080/swagger-ui/index.html

---

## Contenedores Docker

El sistema se ejecuta mediante Docker Compose y levanta:

- api-gateway (puerto 8080)
- MS_PRODUCTOS (puerto 8082)
- MS_VENTAS (puerto 8083)

---

## Endpoint de prueba del API Gateway:

http://localhost:8080/test

Respuesta esperada:
- API Gateway operativo

---

## Instrucciones para ejecutar el sistema

### Opcion 1 — Con Docker

bash
# Compilar cada proyecto
cd EV_3_evRetail-master && mvn clean package -DskipTests
cd ../ev3-ms-venta-master && mvn clean package -DskipTests
cd ../api-gateway && mvn clean package -DskipTests
cd ..

# Levantar todos los servicios
docker-compose up --build


### Opcion 2 — Sin Docker (IntelliJ)

1. Abrir cada proyecto por separado en IntelliJ IDEA
2. Configurar la ruta del Wallet de Oracle en application.properties de cada MS:
properties
spring.datasource.url=jdbc:oracle:thin:@evretail_high?TNS_ADMIN=RUTA_ABSOLUTA/Wallet_evretail

3. Levantar en este orden:
   - MS_PRODUCTOS (puerto 8082)
   - MS_VENTAS (puerto 8083)
   - api-gateway (puerto 8080)
4. Verificar en: http://localhost:8082/swagger-ui/index.html

---

## Requisitos

- Java 17+
- Maven 3.8+
- Docker Desktop (opcional)
- Oracle Cloud Wallet (incluida en cada MS en src/main/resources/Wallet_evretail)

---

## Tecnologías utilizadas

- Java 17
- Spring Boot
- Spring Cloud Gateway
- Spring Data JPA
- Oracle Autonomous Database
- OpenAPI / Swagger
- Docker
- Docker Compose
- Maven
- JUnit 5
- Mockito
- Lombok

---

## Repositorio

https://github.com/4nThonySS/Ev3-Retail
