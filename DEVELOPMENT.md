# Development Guide

## Project Architecture

### Layered Architecture

This project follows a standard 3-tier architecture:

```
┌─────────────────────────────────────────┐
│         REST Controllers Layer          │
│  (Handle HTTP requests/responses)       │
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│         Service Layer                   │
│  (Business logic & data processing)     │
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│      Repository Layer (DAO)             │
│  (Data access & database operations)    │
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│         Database (H2)                   │
│  (Data persistence)                     │
└─────────────────────────────────────────┘
```

### Directory Structure

```
src/
├── main/
│   ├── java/com/ecommerce/app/
│   │   ├── controller/       # REST endpoints
│   │   ├── service/          # Business logic
│   │   ├── model/            # Entity classes
│   │   ├── repository/       # Data access
│   │   └── ECommerceApplication.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/ecommerce/app/
        └── *Test.java        # Unit tests
```

## Running the Application

### Method 1: Using Maven
```bash
mvn spring-boot:run
```

### Method 2: Building JAR and Running
```bash
mvn clean package
java -jar target/ecommerce-app-1.0.0.jar
```

### Method 3: Using IDE
1. Open the project in Eclipse/IntelliJ
2. Right-click on `ECommerceApplication.java`
3. Select "Run As" → "Java Application"

## Database Setup

The application uses H2 in-memory database. Data is reset on application restart.

### Access H2 Console
1. Start the application
2. Navigate to: `http://localhost:8080/h2-console`
3. Connection URL: `jdbc:h2:mem:ecommercedb`
4. Username: `sa`
5. Password: (leave blank)

## Development Workflow

### 1. Clone and Setup
```bash
git clone https://github.com/yourorg/agile-project.git
cd agile-project
mvn clean install
```

### 2. Create Feature Branch
```bash
git checkout develop
git pull origin develop
git checkout -b feature/your-feature
```

### 3. Make Changes
- Edit files in `src/main/java`
- Write tests in `src/test/java`
- Update documentation

### 4. Run Tests
```bash
mvn test
mvn test -Dtest=ProductServiceTest
```

### 5. Build and Verify
```bash
mvn clean install
```

### 6. Commit and Push
```bash
git add .
git commit -m "feat: add new feature"
git push origin feature/your-feature
```

### 7. Create Pull Request
- Go to GitHub
- Create PR from your branch to `develop`
- Add description and request reviewers

## Adding New Features

### Example: Adding a New Entity

#### 1. Create Model Class
```java
@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(unique = true, nullable = false)
    private String email;
}
```

#### 2. Create Repository
```java
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
}
```

#### 3. Create Service
```java
@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
    
    // ... other methods
}
```

#### 4. Create Controller
```java
@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(customerService.saveCustomer(customer));
    }
    
    // ... other endpoints
}
```

#### 5. Add Tests
```java
@Test
void testSaveCustomer() {
    Customer customer = new Customer("John Doe", "john@example.com");
    when(customerRepository.save(any(Customer.class))).thenReturn(customer);
    
    Customer result = customerService.saveCustomer(customer);
    
    assertNotNull(result);
    assertEquals("john@example.com", result.getEmail());
}
```

## Code Style Guidelines

### Naming Conventions
- **Classes**: PascalCase (e.g., `ProductService`)
- **Methods**: camelCase (e.g., `getProductById`)
- **Constants**: UPPER_SNAKE_CASE (e.g., `MAX_QUANTITY`)
- **Variables**: camelCase (e.g., `productName`)

### Code Organization
1. Package declarations
2. Import statements
3. Class/Interface declaration
4. Static fields
5. Instance fields
6. Constructors
7. Methods

### JavaDoc Comments
```java
/**
 * Retrieves a product by its ID
 * 
 * @param id the product ID
 * @return Optional containing the Product if found
 * @throws ProductNotFoundException if product not found (optional)
 */
public Optional<Product> getProductById(Long id) {
    // ...
}
```

## Debugging

### Enable Debug Mode
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--debug"
```

### Using IDE Debugger
1. Set breakpoint by clicking on line number
2. Run as "Debug As" → "Java Application"
3. Use step over/into controls

### View Logs
- Default logs: `INFO` level
- See `application.properties` to adjust levels
- Access logs in console during `mvn spring-boot:run`

## Performance Optimization Tips

1. **Add Database Indexes**: For frequently searched columns
2. **Use Pagination**: For large result sets
3. **Lazy Loading**: Load related data only when needed
4. **Caching**: Cache frequently accessed data

### Example: Pagination
```java
@GetMapping("?page=0&size=10")
public ResponseEntity<Page<Product>> getAllProducts(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<Product> products = productRepository.findAll(pageable);
    return ResponseEntity.ok(products);
}
```

## Common Issues & Solutions

### Issue: Build Fails with Maven
```bash
# Clean and rebuild
mvn clean install -U

# Skip tests if needed
mvn clean install -DskipTests
```

### Issue: Port 8080 Already in Use
```bash
# Change port in application.properties
server.port=8081
```

### Issue: H2 Database Connection Error
- Check `application.properties` settings
- Ensure URL format is correct: `jdbc:h2:mem:ecommercedb`

## Useful Maven Commands

```bash
mvn clean                  # Remove target directory
mvn compile                # Compile source code
mvn test                   # Run unit tests
mvn package                # Package application
mvn install                # Install to local repository
mvn spring-boot:run        # Run Spring Boot app
mvn clean install -U       # Clean install with updates
mvn dependency:tree        # Display dependency tree
```

## Useful Git Commands for Development

```bash
# Before starting
git checkout develop
git pull origin develop

# During development
git status
git diff
git add .
git commit -m "message"

# Before PR
git log --oneline -5
git push origin feature/branch

# After PR
git checkout develop
git pull origin develop
git branch -d feature/branch
```

## IDE Shortcuts (IntelliJ IDEA)

- `Cmd + /` - Toggle line comment
- `Cmd + Shift + /` - Toggle block comment
- `Cmd + B` - Go to definition
- `Cmd + Alt + L` - Format code
- `Cmd + Shift + R` - Run
- `Cmd + Shift + D` - Debug

## Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Maven Documentation](https://maven.apache.org/)
- [GitHub Guides](https://guides.github.com/)
- [Git Documentation](https://git-scm.com/doc)

---

**Last Updated:** May 3, 2026
