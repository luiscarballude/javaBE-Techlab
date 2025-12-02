# TechLab Products API

API REST para la gestión de productos y pedidos, desarrollada con Spring Boot 3, JPA/Hibernate y H2 Database.

## 📚 Documentación Completa

- **[📖 README](README.md)** - Este archivo (Documentación general)
- **[📋 API_REFERENCE.md](API_REFERENCE.md)** - Referencia completa de todos los endpoints

## 🚀 Características

- **CRUD completo de Productos**: Crear, leer, actualizar y eliminar productos
- **Gestión de Pedidos**: Crear pedidos con validación de stock
- **Base de datos H2**: Base de datos en memoria para desarrollo y pruebas
- **Validaciones**: Validaciones de datos con Bean Validation
- **Manejo de excepciones**: Respuestas de error consistentes
- **Consola H2**: Interfaz web para consultar la base de datos

## 📋 Requisitos

- Java 17 o superior
- Maven 3.6 o superior

## 🔧 Instalación y Ejecución

1. **Compilar el proyecto:**
```bash
mvn clean install
```

2. **Ejecutar la aplicación:**
```bash
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8083`

## 🗄️ Base de Datos H2

La consola H2 está disponible en: `http://localhost:8083/h2-console`

**Configuración de conexión:**
- JDBC URL: `jdbc:h2:mem:techlab_db`
- Username: `sa`
- Password: _(dejar vacío)_

## 📚 Endpoints de la API

### Productos

#### Listar todos los productos
```http
GET /api/productos
```

**Respuesta:**
```json
[
  {
    "id": 1,
    "nombre": "Laptop IBM",
    "precio": 1200.0,
    "stock": 5
  }
]
```

#### Obtener producto por ID
```http
GET /api/productos/{id}
```

#### Buscar productos por nombre
```http
GET /api/productos/buscar?nombre=laptop
```

#### Buscar productos con stock bajo
```http
GET /api/productos/stock-bajo?minimo=10
```

#### Crear un nuevo producto
```http
POST /api/productos
Content-Type: application/json

{
  "nombre": "Mouse Inalámbrico",
  "precio": 25.99,
  "stock": 50
}
```

#### Actualizar un producto
```http
PUT /api/productos/{id}
Content-Type: application/json

{
  "nombre": "Mouse Inalámbrico Pro",
  "precio": 29.99,
  "stock": 45
}
```

#### Eliminar un producto
```http
DELETE /api/productos/{id}
```

### Pedidos

#### Listar todos los pedidos
```http
GET /api/pedidos
```

**Respuesta:**
```json
[
  {
    "id": 1,
    "lineasPedido": [
      {
        "id": 1,
        "producto": {
          "id": 1,
          "nombre": "Laptop IBM",
          "precio": 1200.0,
          "stock": 3
        },
        "cantidad": 2
      }
    ],
    "total": 2400.0,
    "fechaCreacion": "2025-11-30T10:30:00"
  }
]
```

#### Obtener pedido por ID
```http
GET /api/pedidos/{id}
```

#### Crear un nuevo pedido
```http
POST /api/pedidos
Content-Type: application/json

{
  "lineas": [
    {
      "productoId": 1,
      "cantidad": 2
    },
    {
      "productoId": 3,
      "cantidad": 1
    }
  ]
}
```

## 🧪 Ejemplos de Uso con cURL

### Crear un producto
```bash
curl -X POST http://localhost:8083/api/productos \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Monitor 27 pulgadas",
    "precio": 350.00,
    "stock": 8
  }'
```

### Listar productos
```bash
curl http://localhost:8083/api/productos
```

### Crear un pedido
```bash
curl -X POST http://localhost:8083/api/pedidos \
  -H "Content-Type: application/json" \
  -d '{
    "lineas": [
      {"productoId": 1, "cantidad": 2},
      {"productoId": 3, "cantidad": 1}
    ]
  }'
```

### Actualizar un producto
```bash
curl -X PUT http://localhost:8083/api/productos/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Laptop IBM ThinkPad",
    "precio": 1250.00,
    "stock": 10
  }'
```

### Eliminar un producto
```bash
curl -X DELETE http://localhost:8083/api/productos/1
```

## 📦 Estructura del Proyecto

```
src/main/java/com/techlab/demo/
├── controller/          # Controladores REST
│   ├── ProductoController.java
│   └── PedidoController.java
├── model/              # Entidades JPA
│   ├── Producto.java
│   ├── Pedido.java
│   └── LineaPedido.java
├── repository/         # Repositorios JPA
│   ├── ProductoRepository.java
│   ├── PedidoRepository.java
│   └── LineaPedidoRepository.java
├── service/            # Lógica de negocio
│   ├── ProductoService.java
│   └── PedidoService.java
├── dto/                # Data Transfer Objects
│   └── CrearPedidoRequest.java
├── exception/          # Excepciones personalizadas
│   ├── ProductoNoEncontradoException.java
│   └── StockInsuficienteException.java
└── DemoApplication.java # Clase principal
```

## 🛠️ Tecnologías Utilizadas

- **Spring Boot 3.2.0**: Framework principal
- **Spring Data JPA**: Persistencia de datos
- **H2 Database**: Base de datos en memoria
- **Maven**: Gestión de dependencias
- **Jakarta Validation**: Validación de datos
- **Jackson**: Serialización JSON

## 📝 Datos Iniciales

Al iniciar la aplicación, se cargan automáticamente los siguientes productos:

1. Laptop IBM - $1200.00 (Stock: 5)
2. Laptop HP - $1200.00 (Stock: 5)
3. Teclado Mecanico - $80.00 (Stock: 15)
4. Teclado Bluethooth - $120.00 (Stock: 10)
5. Teclado Inalambrico - $80.00 (Stock: 15)

## ⚠️ Manejo de Errores

La API maneja los siguientes errores:

- **404 Not Found**: Producto no encontrado
- **400 Bad Request**: Stock insuficiente o datos inválidos
- **500 Internal Server Error**: Error inesperado del servidor

**Ejemplo de respuesta de error:**
```json
{
  "error": "Producto con ID 999 no encontrado."
}
```

## 📱 Pruebas con Postman

Se recomienda usar Postman para probar los endpoints. Puedes importar los ejemplos anteriores o crear tu propia colección.

### Tips para Postman:
1. Crea una variable de entorno `baseUrl` con valor `http://localhost:8083`
2. Usa `{{baseUrl}}/api/productos` en tus requests
3. Configura el header `Content-Type: application/json` para POST y PUT
