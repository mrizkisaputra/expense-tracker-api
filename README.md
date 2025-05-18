# Expense Tracker

## Schema Database

![schema database](./schemadb.png)

## How to running this applications

```shell
    docker compose up -d
    
    mvn spring-boot:run
```

## Endpoint

| No | Endpoints                                                   | Auth Requires | Information                                                                                         |
|----|-------------------------------------------------------------|---------------|-----------------------------------------------------------------------------------------------------|
| 1  | POST ``/api/v1/auth/register``                              | ❌             | [Sign up as a new user](#signup)                                                                    |
| 2  | POST ``/api/v1/auth/login``                                 | ❌             | [Login and get JWT](#login)                                                                         |
| 2  | GET ``/api/v1/users/me``                                    | ✅             | [Take current users](#current_user)                                                                 |
| 3  | GET  ``/api/v1/expenses/{idExpense}``                       | ✅             | [Take detail expenses ](#detail_expenses)                                                           |
| 4  | POST ``/api/v1/expenses``                                   | ✅             | [Add new expenses](#add_new_expenses)                                                               |
| 5  | GET  ``/api/v1/expenses``                                   | ✅             | [Take all user expenses](#take_all_expenses)                                                        |
| 6  | PUT  ``/api/v1/expenses/{idExpense}``                       | ✅             | [Edit user expenses](#edit_expenses)                                                                |
| 7  | DELETE ``/api/v1/expenses/{idExpense}``                     | ✅             | [Remove user expenses](#remove_expenses)                                                            |
| 8  | GET ``/api/v1/expenses?size=10&page=0&sort=createdAt,desc`` | ✅             | [Take all user expenses with pagination and sorting](#take_all_expense_with_pagination_and_sorting) |
| 9  | GET ``/api/v1/expenses?filter=week``                        | ✅             | [Take all filtered expenses](#take_all_filtered_expenses)                                           |
| 10 | GET ``/api/v1/expenses?filter=week&page=0&size=10``         | ✅             | [Take all filtered and pagination expenses](#take_all_filtered_pagination_expenses)                 |

### <a id="signup">Signup as a New User</a>

```json
{
  "email": "string",
  "password": "string"
}
```

### <a id="login">Login and Get a JWT token</a>

```json
{
  "email": "string",
  "password": "string"
}
```

### <a id="current_user">Take Current User</a>

Required Header

```
Authorization: Bearer your token
```

### <a id="detail_expenses">Take Detail Expenses</a>

Required Header

```
Authorization: Bearer your token
```

### <a id="add_new_expenses">Add New Expenses</a>

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

### <a id="take_all_expenses">Take all User Expenses</a>

Required Header

```
Authorization: Bearer your token
```

### <a id="edit_expenses">Edit Expense</a>

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

### <a id="remove_expenses">Remove Expense</a>

Required Header

```
Authorization: Bearer your token
```

### <a id="take_all_expense_with_pagination_and_sorting">Take all Expense with Pagination & Sorting</a>

Required Header

```
Authorization: Bearer your token
```

### <a id="take_all_filtered_expenses">Take all filtered Expenses</a>

last week ``/api/v1/expenses?filter=week``

past month ``/api/v1/expenses?filter=month``

last 3 month ``/api/v1/expenses?filter=3month``

Required Header

```
Authorization: Bearer your token
```

### <a id="take_all_filtered_pagination_expenses">Take all Filtered and Pagination Expenses</a>

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