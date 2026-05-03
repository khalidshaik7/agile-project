# E-Commerce API Documentation

## Base URL
```
http://localhost:8080/api
```

## Authentication
Currently, the API does not require authentication. (Can be added in future versions)

---

## Product Endpoints

### 1. Get All Products
Retrieve a list of all products.

**Endpoint:** `GET /products`

**Response:**
```json
[
  {
    "id": 1,
    "name": "Laptop",
    "description": "High-performance laptop",
    "price": 999.99,
    "quantity": 10,
    "category": "Electronics"
  },
  {
    "id": 2,
    "name": "Mouse",
    "description": "Wireless mouse",
    "price": 29.99,
    "quantity": 50,
    "category": "Accessories"
  }
]
```

**Status Codes:**
- `200 OK` - Products retrieved successfully

---

### 2. Get Product by ID
Retrieve a specific product by its ID.

**Endpoint:** `GET /products/{id}`

**Path Parameters:**
| Parameter | Type | Description |
|-----------|------|-------------|
| id | Long | Product ID |

**Response (Success):**
```json
{
  "id": 1,
  "name": "Laptop",
  "description": "High-performance laptop",
  "price": 999.99,
  "quantity": 10,
  "category": "Electronics"
}
```

**Status Codes:**
- `200 OK` - Product found
- `404 Not Found` - Product not found

---

### 3. Create Product
Create a new product.

**Endpoint:** `POST /products`

**Request Body:**
```json
{
  "name": "Keyboard",
  "description": "Mechanical keyboard",
  "price": 129.99,
  "quantity": 25,
  "category": "Accessories"
}
```

**Response:**
```json
{
  "id": 3,
  "name": "Keyboard",
  "description": "Mechanical keyboard",
  "price": 129.99,
  "quantity": 25,
  "category": "Accessories"
}
```

**Status Codes:**
- `201 Created` - Product created successfully
- `400 Bad Request` - Invalid request body

---

### 4. Update Product
Update an existing product.

**Endpoint:** `PUT /products/{id}`

**Path Parameters:**
| Parameter | Type | Description |
|-----------|------|-------------|
| id | Long | Product ID |

**Request Body:**
```json
{
  "name": "Gaming Keyboard",
  "description": "RGB Mechanical keyboard",
  "price": 149.99,
  "quantity": 20,
  "category": "Accessories"
}
```

**Response:**
```json
{
  "id": 3,
  "name": "Gaming Keyboard",
  "description": "RGB Mechanical keyboard",
  "price": 149.99,
  "quantity": 20,
  "category": "Accessories"
}
```

**Status Codes:**
- `200 OK` - Product updated successfully
- `404 Not Found` - Product not found
- `400 Bad Request` - Invalid request body

---

### 5. Delete Product
Delete a product.

**Endpoint:** `DELETE /products/{id}`

**Path Parameters:**
| Parameter | Type | Description |
|-----------|------|-------------|
| id | Long | Product ID |

**Status Codes:**
- `204 No Content` - Product deleted successfully
- `404 Not Found` - Product not found

---

### 6. Search Products by Category
Search for products by category.

**Endpoint:** `GET /products/search/category/{category}`

**Path Parameters:**
| Parameter | Type | Description |
|-----------|------|-------------|
| category | String | Product category |

**Response:**
```json
[
  {
    "id": 1,
    "name": "Laptop",
    "description": "High-performance laptop",
    "price": 999.99,
    "quantity": 10,
    "category": "Electronics"
  }
]
```

**Status Codes:**
- `200 OK` - Search completed

---

### 7. Search Products by Name
Search for products by name (case-insensitive).

**Endpoint:** `GET /products/search/name/{name}`

**Path Parameters:**
| Parameter | Type | Description |
|-----------|------|-------------|
| name | String | Product name (partial match) |

**Response:**
```json
[
  {
    "id": 1,
    "name": "Laptop",
    "description": "High-performance laptop",
    "price": 999.99,
    "quantity": 10,
    "category": "Electronics"
  }
]
```

**Status Codes:**
- `200 OK` - Search completed

---

## Order Endpoints

### 1. Get All Orders
Retrieve a list of all orders.

**Endpoint:** `GET /orders`

**Response:**
```json
[
  {
    "id": 1,
    "customerName": "John Doe",
    "customerEmail": "john@example.com",
    "totalAmount": 1029.98,
    "status": "CONFIRMED",
    "createdAt": "2026-05-03T10:30:00",
    "updatedAt": "2026-05-03T10:30:00"
  }
]
```

**Status Codes:**
- `200 OK` - Orders retrieved successfully

---

### 2. Get Order by ID
Retrieve a specific order by its ID.

**Endpoint:** `GET /orders/{id}`

**Path Parameters:**
| Parameter | Type | Description |
|-----------|------|-------------|
| id | Long | Order ID |

**Response:**
```json
{
  "id": 1,
  "customerName": "John Doe",
  "customerEmail": "john@example.com",
  "totalAmount": 1029.98,
  "status": "CONFIRMED",
  "createdAt": "2026-05-03T10:30:00",
  "updatedAt": "2026-05-03T10:30:00"
}
```

**Status Codes:**
- `200 OK` - Order found
- `404 Not Found` - Order not found

---

### 3. Create Order
Create a new order.

**Endpoint:** `POST /orders`

**Request Body:**
```json
{
  "customerName": "Jane Smith",
  "customerEmail": "jane@example.com",
  "totalAmount": 199.99
}
```

**Response:**
```json
{
  "id": 2,
  "customerName": "Jane Smith",
  "customerEmail": "jane@example.com",
  "totalAmount": 199.99,
  "status": "PENDING",
  "createdAt": "2026-05-03T11:00:00",
  "updatedAt": "2026-05-03T11:00:00"
}
```

**Status Codes:**
- `201 Created` - Order created successfully
- `400 Bad Request` - Invalid request body

---

### 4. Update Order Status
Update the status of an existing order.

**Endpoint:** `PUT /orders/{id}/status`

**Path Parameters:**
| Parameter | Type | Description |
|-----------|------|-------------|
| id | Long | Order ID |

**Query Parameters:**
| Parameter | Type | Description |
|-----------|------|-------------|
| status | String | Order status (PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED) |

**Example:**
```
PUT /orders/1/status?status=SHIPPED
```

**Response:**
```json
{
  "id": 1,
  "customerName": "John Doe",
  "customerEmail": "john@example.com",
  "totalAmount": 1029.98,
  "status": "SHIPPED",
  "createdAt": "2026-05-03T10:30:00",
  "updatedAt": "2026-05-03T11:15:00"
}
```

**Status Codes:**
- `200 OK` - Order status updated successfully
- `404 Not Found` - Order not found

---

### 5. Delete Order
Delete an order.

**Endpoint:** `DELETE /orders/{id}`

**Path Parameters:**
| Parameter | Type | Description |
|-----------|------|-------------|
| id | Long | Order ID |

**Status Codes:**
- `204 No Content` - Order deleted successfully
- `404 Not Found` - Order not found

---

### 6. Get Orders by Customer Email
Search for orders by customer email address.

**Endpoint:** `GET /orders/search/customer/{email}`

**Path Parameters:**
| Parameter | Type | Description |
|-----------|------|-------------|
| email | String | Customer email address |

**Response:**
```json
[
  {
    "id": 1,
    "customerName": "John Doe",
    "customerEmail": "john@example.com",
    "totalAmount": 1029.98,
    "status": "CONFIRMED",
    "createdAt": "2026-05-03T10:30:00",
    "updatedAt": "2026-05-03T10:30:00"
  }
]
```

**Status Codes:**
- `200 OK` - Search completed

---

### 7. Get Orders by Status
Search for orders by their status.

**Endpoint:** `GET /orders/search/status/{status}`

**Path Parameters:**
| Parameter | Type | Description |
|-----------|------|-------------|
| status | String | Order status (PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED) |

**Response:**
```json
[
  {
    "id": 1,
    "customerName": "John Doe",
    "customerEmail": "john@example.com",
    "totalAmount": 1029.98,
    "status": "SHIPPED",
    "createdAt": "2026-05-03T10:30:00",
    "updatedAt": "2026-05-03T11:15:00"
  }
]
```

**Status Codes:**
- `200 OK` - Search completed

---

## Error Responses

All error responses follow this format:

```json
{
  "timestamp": "2026-05-03T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Resource not found",
  "path": "/api/products/999"
}
```

---

## Example Usage with cURL

### Get all products
```bash
curl -X GET http://localhost:8080/api/products
```

### Create a new product
```bash
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Monitor",
    "description": "4K Monitor",
    "price": 399.99,
    "quantity": 15,
    "category": "Electronics"
  }'
```

### Get order by ID
```bash
curl -X GET http://localhost:8080/api/orders/1
```

### Update order status
```bash
curl -X PUT "http://localhost:8080/api/orders/1/status?status=SHIPPED" \
  -H "Content-Type: application/json"
```

---

**Last Updated:** May 3, 2026
