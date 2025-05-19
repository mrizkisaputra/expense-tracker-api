# Expense Tracker

## Schema Database

![schema database](./schemadb.png)

## How to running this applications

```shell
    docker compose up -d
    
    mvn spring-boot:run
```

## Endpoint

| No | Endpoints                                                   | Auth Requires | Authorize                  | Information                                  |
|----|-------------------------------------------------------------|---------------|----------------------------|----------------------------------------------|
| 1  | POST ``/api/v1/auth/register``                              | ❌             | -                          | [Sign up as a new user](#signup)             |
| 2  | POST ``/api/v1/auth/login``                                 | ❌             | -                          | [Login and get JWT](#login)                  |
| 3  | GET ``/api/v1/users/me``                                    | ✅             | (USER, ADMIN, SUPER_ADMIN) | [Take authenticated users](#current_user)    |
| 4  | GET ``/api/v1/users``                                       | ✅             | (ADMIN, SUPER_ADMIN)       | [Take all users have role User](#take_all_user)           |
| 5  | GET  ``/api/v1/expenses/{idExpense}``                       | ✅             | (USER)                     | [Take detail expenses ](#detail_expenses)    |
| 6  | POST ``/api/v1/expenses``                                   | ✅             | (USER)                     | [Add new expenses](#add_new_expenses)        |
| 7  | GET  ``/api/v1/expenses``                                   | ✅             | (USER)                     | [Take all user expenses](#take_all_expenses) |
| 8  | PUT  ``/api/v1/expenses/{idExpense}``                       | ✅             | (USER)                     | [Edit user expenses](#edit_expenses)         |
| 9  | DELETE ``/api/v1/expenses/{idExpense}``                     | ✅             | (USER)                     | [Remove user expenses](#remove_expenses)     |

### <a id="signup">1. Signup as a New User</a>

```json
{
  "email": "string",
  "password": "string"
}
```

### <a id="login">2. Login and Get a JWT token</a>

```json
{
  "email": "string",
  "password": "string"
}
```

### <a id="current_user">3. Take Authenticated User</a>

Required Header

```
Authorization: Bearer your token
```

### <a id="take_all_user">4. Take all User have role user</a>

**Take all users with pagination ```/api/v1/users?size=10&page=0```**

Required Header

```
Authorization: Bearer your token
```

### <a id="detail_expenses">5. Take Detail Expenses</a>

Required Header

```
Authorization: Bearer your token
```

### <a id="add_new_expenses">6. Add New Expenses</a>

Required Header

```
Authorization: Bearer your token
```

```json
{
  "description": "string",
  "amount": "number",
  "category": "string"
}
```

### <a id="take_all_expenses">7. Take all User Expenses</a>

**Take all User Expense with pagination and sorting ```/api/v1/expenses?size=10&page=0&sort=createdAt,desc```**

**Take all filtered User Expense**

- last week ```/api/v1/expenses?filter=week```
- past month ```/api/v1/expenses?filter=month```
- last 3 month ```/api/v1/expenses?filter=3month```

**Take all Filtered and Pagination Expenses**
```/api/v1/expenses?filter=week&size=10&page=0&sort=createdAt,desc```

Required Header

```
Authorization: Bearer your token
```

### <a id="edit_expenses">8. Edit Expense</a>

Required Header

```
Authorization: Bearer your token
```

```json
{
  "description": "string",
  "amount": "number",
  "category": "string"
}
```

### <a id="remove_expenses">9. Remove Expense</a>

Required Header

```
Authorization: Bearer your token
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

[Menerapkan token based autentikasi JWT dalam aplikasi Spring Boot 3](https://medium.com/@tericcabrel/implement-jwt-authentication-in-a-spring-boot-3-application-5839e4fd8fac)