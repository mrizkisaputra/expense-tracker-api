# Expense Tracker

## Features

| No        | Features                                | Auth Requires | Information            |
|-----------|-----------------------------------------|---------------|------------------------|
| [1](satu) | POST ``/api/v1/auth/register``          | ❌             | Sign up as a new user  |
| [2](2)    | POST ``/api/v1/auth/login``             | ❌             | Login and get JWT      |
| [3](3)    | GET  ``/api/v1/expenses/{idExpense}``   | ✅             | Take detail expenses   |
| [4](4)    | POST ``/api/v1/expenses``               | ✅             | Add new expenses       |
| [5](5)    | GET  ``/api/v1/expenses``               | ✅             | Take all user expenses |
| [6](6)    | PUT  ``/api/v1/expenses/{idExpense}``   | ✅             | Edit user expenses     |
| [7](7)    | DELETE ``/api/v1/expenses/{idExpense}`` | ✅             | Remove user expenses   |
| [8](8)    | GET ``/api/v1/expenses?``               | ✅             |                        |

## How to running di applications
```shell
    docker compose up -d
    
    mvn spring-boot:run
```

## Tech Stack

1. Spring Framework
    - Spring Boot Starter Web
    - Spring Boot Starter Data JPA
    - Spring Boot Starter Validation
    - Spring Boot Starter Security

2. Database
    - Postgresql

3. Migration Tools
    - Flyway

4. JWT
    - jjwt api
    - jjwt jackson
    - jjwt impl

5. Utillities
    - Lombok