# E-Commerce Application

## Overview
A simple Spring Boot e-commerce project designed as a demonstration of how GitHub works in a development team environment. This project includes practical examples of Git workflows, branching strategies, collaborative development practices, and automated CI/CD pipelines.

## Project Description
This is a basic e-commerce application built with Spring Boot that provides REST APIs for managing:
- **Products**: CRUD operations for product catalog management
- **Orders**: Create and manage customer orders
- **Users**: User registration and authentication with JWT
- **Authentication**: Secure login and registration system

The application uses:
- Spring Boot 3.1.5
- Spring Data JPA with Hibernate
- Spring Security with JWT (JSON Web Tokens)
- H2 In-Memory Database
- Maven build tool
- Lombok for reducing boilerplate code

## Project Structure
```
agile-project/
├── src/
│   ├── main/
│   │   ├── java/com/ecommerce/app/
│   │   │   ├── controller/          # REST Controllers
│   │   │   ├── service/             # Business Logic
│   │   │   ├── model/               # Entity Classes
│   │   │   ├── repository/          # Data Access Layer
│   │   │   ├── dto/                 # Data Transfer Objects
│   │   │   ├── security/            # Security Configuration
│   │   │   └── ECommerceApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/ecommerce/app/
├── .github/
│   ├── workflows/
│   │   ├── maven-build.yml          # Build and Test CI
│   │   ├── codeql-analysis.yml      # Security Analysis
│   │   └── copilot-review.yml       # Copilot Code Review
│   └── pull_request_template.md
├── pom.xml                           # Maven Configuration
├── .gitignore                        # Git ignore rules
├── AUTHENTICATION.md                 # Authentication Guide
├── README.md
└── Other documentation files
```

## Prerequisites
- Java 17 or higher
- Maven 3.6+
- Git
- GitHub account (for workflows)

## Getting Started

### 1. Clone the Repository
```bash
git clone https://github.com/yourusername/agile-project.git
cd agile-project
```

### 2. Build the Project
```bash
mvn clean install
```

### 3. Run the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### 4. Access H2 Database Console (Optional)
Navigate to: `http://localhost:8080/h2-console`
- URL: `jdbc:h2:mem:ecommercedb`
- Username: `sa`
- Password: (leave blank)

## API Endpoints

### Authentication Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register new user |
| POST | `/api/auth/login` | Login and get JWT token |
| GET | `/api/auth/health` | Health check |

### Product Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/products` | Get all products |
| GET | `/api/products/{id}` | Get product by ID |
| POST | `/api/products` | Create new product |
| PUT | `/api/products/{id}` | Update product |
| DELETE | `/api/products/{id}` | Delete product |
| GET | `/api/products/search/category/{category}` | Search by category |
| GET | `/api/products/search/name/{name}` | Search by name |

### Order Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/orders` | Get all orders |
| GET | `/api/orders/{id}` | Get order by ID |
| POST | `/api/orders` | Create new order |
| PUT | `/api/orders/{id}/status` | Update order status |
| DELETE | `/api/orders/{id}` | Delete order |
| GET | `/api/orders/search/customer/{email}` | Search by customer email |
| GET | `/api/orders/search/status/{status}` | Search by order status |

## GitHub Actions Workflows

### 🔨 Maven Build and Test (`maven-build.yml`)
- **Trigger**: Push to `main`/`develop`, Pull Requests
- **Actions**:
  - Sets up Java 17 environment
  - Builds project with Maven
  - Runs unit tests
  - Uploads test results as artifacts
  - Uploads built JAR file

```yaml
# Runs automatically on:
on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main, develop ]
```

### 🔐 CodeQL Security Analysis (`codeql-analysis.yml`)
- **Trigger**: Push to `main`/`develop`, Pull Requests, Weekly schedule
- **Actions**:
  - Initializes CodeQL analysis
  - Scans Java code for security vulnerabilities
  - Generates security reports
  - Comments on PRs with findings

```yaml
# Runs automatically on:
on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main, develop ]
  schedule:
    - cron: '0 2 * * 0'  # Weekly on Sunday at 2 AM
```

### 🤖 Copilot Code Review (`copilot-review.yml`)
- **Trigger**: Pull Requests to `main`/`develop`
- **Actions**:
  - Detects changed Java files
  - Builds changed code
  - Performs code quality checks
  - Posts automated review comment on PR
  - Provides code review checklist

```yaml
# Runs automatically on:
on:
  pull_request:
    branches: [ main, develop ]
```

## GitHub Workflow Guide for Development Teams

### Understanding Git Basics

#### 1. **Initial Setup**
```bash
# Configure your Git identity
git config --global user.name "Your Name"
git config --global user.email "your.email@example.com"

# Verify configuration
git config --list
```

#### 2. **Cloning the Repository**
```bash
git clone https://github.com/yourorg/agile-project.git
cd agile-project
```

#### 3. **Checking Repository Status**
```bash
# View current branch and changes
git status

# View detailed changes
git diff

# View commit history
git log --oneline
```

### Branching Strategy (Git Flow)

This project follows **Git Flow** branching model:

#### Branch Types:
- **main**: Production-ready code (protected branch)
- **develop**: Development branch (integration branch)
- **feature/**: New features (branch from `develop`)
- **bugfix/**: Bug fixes (branch from `develop`)
- **hotfix/**: Critical production fixes (branch from `main`)
- **release/**: Release preparation (branch from `develop`)

#### Creating a Feature Branch
```bash
# Update develop branch
git checkout develop
git pull origin develop

# Create feature branch
git checkout -b feature/user-authentication

# Branch naming convention: feature/description-of-feature
```

#### Working on Your Branch
```bash
# Make changes to files
# Check what changed
git status

# Stage specific files
git add src/main/java/com/ecommerce/app/controller/AuthController.java

# Or stage all changes
git add .

# Commit with meaningful message
git commit -m "feat: add user authentication system

- Implement UserService with login and registration
- Add AuthController with JWT endpoints
- Configure Spring Security
- Add unit tests for authentication"

# Commit message convention:
# feat: new feature
# fix: bug fix
# docs: documentation
# style: formatting
# refactor: code refactoring
# test: adding tests
```

#### Pushing to Remote
```bash
# Push branch to remote repository
git push origin feature/user-authentication

# Set upstream for easier pushing in future
git push -u origin feature/user-authentication
```

### Pull Requests (PRs)

#### 1. **Creating a Pull Request**
After pushing your feature branch:
1. Go to GitHub repository
2. Click "Compare & pull request"
3. Set base branch to `develop`
4. Fill out the PR template completely
5. Request reviewers from team
6. Link related issues
7. Click "Create pull request"

#### 2. **What Happens Automatically**
When you create a PR:
- ✅ Maven build runs automatically
- 🔐 CodeQL security scan begins
- 🤖 Copilot review analyzes your code
- 📊 Test results display in the PR
- 🔍 Reviewers are notified

#### 3. **Code Review Process**
```bash
# Reviewers can pull the branch locally
git fetch origin feature/user-authentication
git checkout -b review/user-authentication origin/feature/user-authentication

# Test the changes
mvn clean install
mvn spring-boot:run

# Review code, run tests, then provide feedback on GitHub
```

#### 4. **Addressing Review Comments**
```bash
# Make requested changes
# Commit with reference to PR
git add .
git commit -m "review: address feedback from PR #42

- Renamed variable for clarity
- Added error handling
- Updated documentation"

# Push changes - automated workflows run again
git push origin feature/user-authentication
```

#### 5. **Merging the PR**
Once approved and all checks pass:
1. All required workflows must succeed
2. At least 1-2 approvals needed
3. Click "Merge pull request" on GitHub
4. Automatic PR template ensures completeness
5. Branch is deleted after merge

### Handling Merge Conflicts

```bash
# If conflict occurs during merge
git status

# Open conflicted files and resolve manually
# Look for conflict markers:
# <<<<<<< HEAD
# current branch changes
# =======
# incoming changes
# >>>>>>> feature/branch-name

# After resolving
git add .
git commit -m "resolve: merge conflict in ProductService"
git push origin feature/branch-name
```

### Best Practices

✅ **DO:**
- Pull before you start work: `git pull origin develop`
- Create descriptive commit messages
- Keep commits small and focused
- Push regularly to backup work
- Review your own changes before requesting review
- Keep branches short-lived (merge within 3-5 days)
- Use meaningful branch names
- Write unit tests for new features
- Check CodeQL and build results before requesting review

❌ **DON'T:**
- Force push to shared branches: Use `--force-with-lease` instead
- Commit large binary files
- Commit secrets or passwords
- Mix multiple features in one PR
- Ignore workflow failures
- Skip code reviews
- Merge with failing tests

### Git Commands Cheat Sheet

```bash
# Essential Commands
git status                          # Check repository status
git add <file>                      # Stage changes
git commit -m "message"             # Commit changes
git push origin <branch>            # Push to remote
git pull origin <branch>            # Fetch and merge from remote
git branch <branch-name>            # Create new branch
git checkout <branch-name>          # Switch branch
git merge <branch-name>             # Merge branch
git log --oneline                   # View commit history
git diff                            # View changes
git stash                           # Temporarily save changes
git stash pop                       # Restore stashed changes
```

## Authentication Feature

For detailed authentication documentation, see [AUTHENTICATION.md](AUTHENTICATION.md)

### Quick Start - Authentication

**Register a User:**
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

## Running Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=UserServiceTest

# Run with code coverage
mvn test jacoco:report
```

## Issues and Bug Reports

### Creating an Issue
1. Go to GitHub Issues tab
2. Click "New Issue"
3. Use clear, descriptive title
4. Include reproduction steps for bugs
5. Label appropriately (bug, enhancement, documentation)
6. Assign to team member if needed

### Linking Issues to Commits
```bash
# Reference issue in commit message
git commit -m "fix: resolve login bug - closes #15"
```

## Project Roadmap

- [x] Basic CRUD operations for Products
- [x] Order management system
- [x] User authentication and registration
- [x] GitHub Actions CI/CD pipelines
- [x] CodeQL security scanning
- [ ] Email verification
- [ ] Password reset functionality
- [ ] Role-based access control (RBAC)
- [ ] OAuth2 integration
- [ ] Payment gateway integration
- [ ] Deployment automation

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request and wait for reviews and CI/CD checks

## License

This project is licensed under the MIT License - see LICENSE file for details.

## Contact

For questions or support, please open an issue on GitHub or contact the development team.

## Documentation

- [AUTHENTICATION.md](AUTHENTICATION.md) - Authentication and user registration guide
- [QUICK_START.md](QUICK_START.md) - Quick start guide
- [API_DOCUMENTATION.md](API_DOCUMENTATION.md) - Detailed API documentation
- [DEVELOPMENT.md](DEVELOPMENT.md) - Development setup guide
- [CONTRIBUTING.md](CONTRIBUTING.md) - Contributing guidelines
- [PROJECT_STRUCTURE.md](PROJECT_STRUCTURE.md) - Project structure details

---

**Last Updated**: May 3, 2026
**Version**: 1.0.0
**Status**: Active Development
