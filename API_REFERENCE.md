# API Reference - TechLab Products API

Documentación completa de todos los endpoints disponibles en la API.

**Base URL:** `http://localhost:8083`

## 📦 Productos

### Listar todos los productos

Obtiene la lista completa de productos disponibles.

```http
GET /api/productos
```

**Respuesta exitosa (200 OK):**
```json
[
  {
    "id": 1,
    "nombre": "Laptop IBM",
    "precio": 1200.0,
    "stock": 5
  },
  {
    "id": 2,
    "nombre": "Laptop HP",
    "precio": 1200.0,
    "stock": 5
  },
  {
    "id": 3,
    "nombre": "Teclado Mecanico",
    "precio": 80.0,
    "stock": 15
  }
]
```

---

### Obtener producto por ID

Obtiene los detalles de un producto específico.

```http
GET /api/productos/{id}
```

**Parámetros de ruta:**
- `id` (Long) - ID del producto

**Ejemplo:**
```bash
curl http://localhost:8083/api/productos/1
```

**Respuesta exitosa (200 OK):**
```json
{
  "id": 1,
  "nombre": "Laptop IBM",
  "precio": 1200.0,
  "stock": 5
}
```

**Respuesta de error (404 Not Found):**
```json
{
  "error": "Producto con ID 999 no encontrado."
}
```

---

### Buscar productos por nombre

Busca productos cuyo nombre contenga el texto especificado (case-insensitive).

```http
GET /api/productos/buscar?nombre={nombre}
```

**Parámetros de consulta:**
- `nombre` (String) - Texto a buscar en el nombre del producto

**Ejemplo:**
```bash
curl "http://localhost:8083/api/productos/buscar?nombre=laptop"
```

**Respuesta exitosa (200 OK):**
```json
[
  {
    "id": 1,
    "nombre": "Laptop IBM",
    "precio": 1200.0,
    "stock": 5
  },
  {
    "id": 2,
    "nombre": "Laptop HP",
    "precio": 1200.0,
    "stock": 5
  }
]
```

---

### Buscar productos con stock bajo

Busca productos cuyo stock sea menor al valor especificado.

```http
GET /api/productos/stock-bajo?minimo={minimo}
```

**Parámetros de consulta:**
- `minimo` (Integer, default: 10) - Stock mínimo para el filtro

**Ejemplo:**
```bash
curl "http://localhost:8083/api/productos/stock-bajo?minimo=10"
```

**Respuesta exitosa (200 OK):**
```json
[
  {
    "id": 1,
    "nombre": "Laptop IBM",
    "precio": 1200.0,
    "stock": 5
  },
  {
    "id": 2,
    "nombre": "Laptop HP",
    "precio": 1200.0,
    "stock": 5
  }
]
```

---

### Crear un nuevo producto

Crea un nuevo producto en el sistema.

```http
POST /api/productos
Content-Type: application/json
```

**Cuerpo de la petición:**
```json
{
  "nombre": "Mouse Inalámbrico",
  "precio": 25.99,
  "stock": 50
}
```

**Validaciones:**
- `nombre`: No puede estar vacío
- `precio`: Debe ser >= 0
- `stock`: Debe ser >= 0

**Ejemplo:**
```bash
curl -X POST http://localhost:8083/api/productos \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Mouse Inalámbrico",
    "precio": 25.99,
    "stock": 50
  }'
```

**Respuesta exitosa (201 Created):**
```json
{
  "id": 6,
  "nombre": "Mouse Inalámbrico",
  "precio": 25.99,
  "stock": 50
}
```

**Respuesta de error de validación (400 Bad Request):**
```json
{
  "error": "El nombre del producto es obligatorio"
}
```

---

### Actualizar un producto

Actualiza los datos de un producto existente. Solo los campos proporcionados serán actualizados.

```http
PUT /api/productos/{id}
Content-Type: application/json
```

**Parámetros de ruta:**
- `id` (Long) - ID del producto a actualizar

**Cuerpo de la petición:**
```json
{
  "nombre": "Mouse Inalámbrico Pro",
  "precio": 29.99,
  "stock": 45
}
```

**Ejemplo:**
```bash
curl -X PUT http://localhost:8083/api/productos/6 \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Mouse Inalámbrico Pro",
    "precio": 29.99,
    "stock": 45
  }'
```

**Respuesta exitosa (200 OK):**
```json
{
  "id": 6,
  "nombre": "Mouse Inalámbrico Pro",
  "precio": 29.99,
  "stock": 45
}
```

**Respuesta de error (404 Not Found):**
```json
{
  "error": "Producto con ID 999 no encontrado."
}
```

---

### Eliminar un producto

Elimina un producto del sistema.

```http
DELETE /api/productos/{id}
```

**Parámetros de ruta:**
- `id` (Long) - ID del producto a eliminar

**Ejemplo:**
```bash
curl -X DELETE http://localhost:8083/api/productos/6
```

**Respuesta exitosa (200 OK):**
```json
{
  "mensaje": "Producto con ID 6 eliminado con éxito"
}
```

**Respuesta de error (404 Not Found):**
```json
{
  "error": "Producto con ID 999 no encontrado."
}
```

---

## 🛒 Pedidos

### Listar todos los pedidos

Obtiene la lista completa de pedidos, ordenados por fecha de creación descendente.

```http
GET /api/pedidos
```

**Ejemplo:**
```bash
curl http://localhost:8083/api/pedidos
```

**Respuesta exitosa (200 OK):**
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

---

### Obtener pedido por ID

Obtiene los detalles de un pedido específico.

```http
GET /api/pedidos/{id}
```

**Parámetros de ruta:**
- `id` (Long) - ID del pedido

**Ejemplo:**
```bash
curl http://localhost:8083/api/pedidos/1
```

**Respuesta exitosa (200 OK):**
```json
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
    },
    {
      "id": 2,
      "producto": {
        "id": 3,
        "nombre": "Teclado Mecanico",
        "precio": 80.0,
        "stock": 14
      },
      "cantidad": 1
    }
  ],
  "total": 2480.0,
  "fechaCreacion": "2025-11-30T10:30:00"
}
```

**Respuesta de error (404 Not Found):**
```json
{
  "error": "Pedido con ID 999 no encontrado."
}
```

---

### Crear un nuevo pedido

Crea un nuevo pedido con las líneas de pedido especificadas. El sistema valida que haya stock suficiente antes de crear el pedido.

```http
POST /api/pedidos
Content-Type: application/json
```

**Cuerpo de la petición:**
```json
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

**Ejemplo:**
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

**Respuesta exitosa (201 Created):**
```json
{
  "id": 2,
  "lineasPedido": [
    {
      "id": 3,
      "producto": {
        "id": 1,
        "nombre": "Laptop IBM",
        "precio": 1200.0,
        "stock": 1
      },
      "cantidad": 2
    },
    {
      "id": 4,
      "producto": {
        "id": 3,
        "nombre": "Teclado Mecanico",
        "precio": 80.0,
        "stock": 13
      },
      "cantidad": 1
    }
  ],
  "total": 2480.0,
  "fechaCreacion": "2025-11-30T11:15:00"
}
```

**Nota:** El stock de los productos se actualiza automáticamente al crear el pedido.

**Respuesta de error - Stock insuficiente (400 Bad Request):**
```json
{
  "error": "Stock insuficiente para Laptop IBM. Stock disponible: 1"
}
```

**Respuesta de error - Producto no encontrado (404 Not Found):**
```json
{
  "error": "Producto con ID 999 no encontrado."
}
```

---

## 📊 Códigos de Estado HTTP

| Código | Descripción | Cuándo se usa |
|--------|-------------|---------------|
| 200 OK | Solicitud exitosa | GET, PUT, DELETE exitosos |
| 201 Created | Recurso creado | POST exitoso |
| 400 Bad Request | Datos inválidos | Validación fallida, stock insuficiente |
| 404 Not Found | Recurso no encontrado | ID no existe |
| 500 Internal Server Error | Error del servidor | Error inesperado |

---

## 🔐 Seguridad (Futuras Mejoras)

Actualmente la API no tiene autenticación. Para producción se recomienda agregar:

- JWT para autenticación
- Roles y permisos
- Rate limiting
- CORS configuration

---

## 📝 Notas Importantes

### Transacciones
- Todas las operaciones que modifican datos están envueltas en transacciones
- Si algo falla al crear un pedido, los cambios se revierten automáticamente

### Base de Datos
- Los datos se almacenan en H2 (en memoria)
- Los datos se pierden al reiniciar la aplicación
- Para persistencia permanente, cambia a PostgreSQL o MySQL

### Validaciones
- Los precios y stocks no pueden ser negativos
- Los nombres de productos no pueden estar vacíos
- Las cantidades en pedidos deben ser positivas

### Actualización de Stock
- El stock se descuenta automáticamente al crear un pedido
- Si hay stock insuficiente, el pedido no se crea
- El stock se valida para TODAS las líneas antes de descontar

---

## 🧪 Ejemplos de Flujo Completo

### Flujo 1: Crear y actualizar un producto

```bash
# 1. Crear producto
curl -X POST http://localhost:8083/api/productos \
  -H "Content-Type: application/json" \
  -d '{"nombre": "Webcam HD", "precio": 45.00, "stock": 20}'

# Respuesta: {"id": 6, ...}

# 2. Verificar que se creó
curl http://localhost:8083/api/productos/6

# 3. Actualizar precio
curl -X PUT http://localhost:8083/api/productos/6 \
  -H "Content-Type: application/json" \
  -d '{"precio": 39.99}'

# 4. Verificar actualización
curl http://localhost:8083/api/productos/6
```

### Flujo 2: Crear un pedido completo

```bash
# 1. Ver productos disponibles
curl http://localhost:8083/api/productos

# 2. Crear pedido
curl -X POST http://localhost:8083/api/pedidos \
  -H "Content-Type: application/json" \
  -d '{
    "lineas": [
      {"productoId": 1, "cantidad": 1},
      {"productoId": 3, "cantidad": 2}
    ]
  }'

# Respuesta: {"id": 1, "total": 1360.0, ...}

# 3. Verificar que el stock se actualizó
curl http://localhost:8083/api/productos/1
# Stock ahora es 4 (era 5)

# 4. Ver el pedido creado
curl http://localhost:8083/api/pedidos/1
```

---

**Versión de la API:** 1.0.0  
**Última actualización:** Noviembre 2025
