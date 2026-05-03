# Authentication & User Registration

This document describes the authentication and user registration features of the E-Commerce Application.

## Overview

The application implements JWT (JSON Web Token) based authentication with user registration and login capabilities. All authentication endpoints are located at `/api/auth/`.

## Features

### 1. User Registration
- Create new user accounts
- Email and username uniqueness validation
- Password hashing using BCrypt
- Automatic timestamp tracking

### 2. User Login
- Authenticate users with username and password
- JWT token generation
- Token-based session management

### 3. Security
- Password encryption using BCrypt
- JWT token expiration (configurable)
- CORS support for frontend integration
- Spring Security integration

## API Endpoints

### Register User

**Endpoint:** `POST /api/auth/register`

**Request Body:**
```json
{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "SecurePassword123!",
  "firstName": "John",
  "lastName": "Doe"
}
```

**Response (201 Created):**
```json
{
  "message": "User registered successfully",
  "userId": 1,
  "username": "john_doe",
  "email": "john@example.com"
}
```

**Error Response (400 Bad Request):**
```json
{
  "message": "Registration failed: Username already exists"
}
```

### Login User

**Endpoint:** `POST /api/auth/login`

**Request Body:**
```json
{
  "username": "john_doe",
  "password": "SecurePassword123!"
}
```

**Response (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "username": "john_doe",
  "email": "john@example.com",
  "userId": 1
}
```

**Error Response (401 Unauthorized):**
```json
{
  "username": "john_doe"
}
```

### Health Check

**Endpoint:** `GET /api/auth/health`

**Response (200 OK):**
```
Auth service is running
```

## Configuration

### JWT Configuration

Update `application.properties`:
```properties
# JWT Configuration
app.jwt.secret=your-secret-key-here
app.jwt.expiration=86400000  # 24 hours in milliseconds
```

### Database Configuration

The application uses H2 in-memory database by default. Access the H2 console at `http://localhost:8080/h2-console`

**H2 Console Credentials:**
- JDBC URL: `jdbc:h2:mem:ecommercedb`
- User: `sa`
- Password: (empty)

## Usage Example

### Using cURL

**Register:**
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "email": "john@example.com",
    "password": "SecurePassword123!",
    "firstName": "John",
    "lastName": "Doe"
  }'
```

**Login:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "password": "SecurePassword123!"
  }'
```

### Using JWT Token

Once logged in, include the token in your requests:

```bash
curl -X GET http://localhost:8080/api/products \
  -H "Authorization: Bearer <token>"
```

## Security Considerations

1. **Password Storage:** Passwords are hashed using BCrypt with a strength of 10
2. **Token Expiration:** Tokens expire after 24 hours by default
3. **CORS:** Configured to accept requests from all origins (update for production)
4. **HTTPS:** Always use HTTPS in production
5. **Secret Key:** Use a strong, random secret key in production

## Implementation Details

### User Model
- `id`: Unique identifier (auto-generated)
- `username`: Unique username (required)
- `email`: Unique email address (required)
- `password`: Hashed password (required)
- `firstName`: User's first name (required)
- `lastName`: User's last name (required)
- `enabled`: Account enabled flag (default: true)
- `createdAt`: Account creation timestamp
- `updatedAt`: Last update timestamp

### Classes

- `User`: JPA entity representing a user
- `UserRepository`: Spring Data JPA repository for User
- `UserService`: Business logic for authentication
- `AuthController`: REST API endpoints
- `JwtUtils`: JWT token generation and validation
- `SecurityConfig`: Spring Security configuration

## Testing

Run tests using Maven:
```bash
mvn test
```

Key test cases:
- User registration with valid data
- Duplicate username/email rejection
- Successful login
- Invalid password rejection
- JWT token generation and validation

## Future Enhancements

- [ ] Email verification
- [ ] Password reset functionality
- [ ] Role-based access control (RBAC)
- [ ] OAuth2 integration
- [ ] Two-factor authentication
- [ ] Account lockout after failed attempts
