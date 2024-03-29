# Expense Tracker Application

This repository implements the REST API for an application that tracks a user's expenses. A PostgreSQL database is used.

**Entities**

- `User`
  - POST - /api/v1/users/register
  - POST - /api/v1/users/login
  - GET - /api/v1/users
  - GET - /api/v1/users/{id}
  - PUT - /api/v1/users/{id}
  - DELETE - /api/v1/users/{id}
  
- `Admin`
  - POST - /api/v1/admin/register
  - POST - /api/v1/admin/login
  - GET - /api/v1/admin
  - GET - /api/v1/admin/{id}
  - PUT - /api/v1/admin/{id}
  - DELETE - /api/v1/admin/{id}
  
- `Category`
  - POST - /api/v1/category/create
  - GET - /api/v1/categories
  - GET - /api/v1/category/{id}
  - PUT - /api/v1/category/{id}
  - DELETE - /api/v1/category/{id}

- `Transaction`
  - POST - /api/v1/transaction/create
  - GET - /api/v1/transactions
  - GET - /api/v1/transaction/{id}
  - PUT - /api/v1/transaction/{id}
  - DELETE - /api/v1/transaction/{id}
  