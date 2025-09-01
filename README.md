# Dev-Connect

> This is my first real-world project — a **social network backend for developers** built with Spring Boot.

---

## 📖 About Dev-Connect
Dev-Connect is a backend application that enables developers to:
- Register and manage their profiles
- Post content
- Comment on posts
- Follow and unfollow other users
- Send and receive messages
- Search **users** by tech-stack and domain
- Search **posts** by type (e.g., Internship, Issue – limited types) or by tags (e.g., Java, SpringBoot)
- Secure endpoints using JWT authentication
- Explore and test APIs via Swagger/OpenAPI

It’s built as part of a **real-world learning journey** using Spring Boot and REST APIs.

---

## 🛠 Tech Stack
- **Java 21** (Spring Boot modules: Spring MVC, Spring Data JPA, Spring Security)
- **MySQL 8** or **H2** (Hibernate)
- **Maven**
- **Lombok** & **ModelMapper** (optional)
- **JWT** for authentication
- **Swagger/OpenAPI** for API documentation
- **Git** for version control

---

## 📦 Modules

### 1️⃣ 🔑 Auth Module
**Features**
- Register user with roles, profile, and encoded password
- Login and receive a JWT token
- Fetch currently logged-in user  
  **Edge Cases**
- Duplicate email/username
- Invalid role IDs
- Weak or empty password
- Wrong credentials / invalid JWT

---

### 2️⃣ 👤 User Module
**Features**
- Update own profile (email, password, roles, profile info)
- Delete own account
- View user by ID
- View all users with pagination & sorting
- Admin: update or delete any user

📂 **Search**
- Search by **domain** (e.g., `Backend Developer`, `DevOps Engineer`)
- Search by **tech-stack** (e.g., `Java`, `Spring Boot`, `MySQL`)

**Edge Cases**
- User not found by ID or email
- Unauthorized update/delete
- Empty/invalid fields on update
- Invalid role mapping
- Pagination out-of-range

---

### 3️⃣ 📝 Post Module
**Features**
- Create, edit, and delete own posts
- View all posts with pagination & sorting
- View all posts by a specific user
- Get a single post by ID
- Post responses include **comment count**
- Admin: edit or delete any post

📂 **Search**
- Search by **type** (limited: `Internship`, `Issue`, etc.)
- Search by **tags** (e.g., `#Java`, `#SpringBoot`)

**Edge Cases**
- Post not found by ID
- Unauthorized edit/delete
- Empty/invalid title or content
- Pagination out of range
- Duplicate/invalid post IDs

---

### 4️⃣ 💬 Comment Module
**Features**
- Add a comment to any post
- Delete a comment if: you wrote it OR it was written on your post
- View all comments of a specific post
- Admin: delete any comment  
  **Edge Cases**
- Post not found by ID
- Comment not found by ID
- Unauthorized delete
- Empty/invalid comment content

---

### 5️⃣ 🔗 Connection Module
**Features**
- Follow another user
- Unfollow a user
- Get follower and following count for any user  
  **Edge Cases**
- Cannot follow or unfollow yourself
- Already following / not following users
- User not found by ID or email

---

### 6️⃣ 💌 Message Module
**Features**
- Send messages to another user
- View all received messages
- View all sent messages
- View conversation with a specific user
- Delete own messages

**Edge Cases**
- Receiver or sender not found
- Message not found by ID
- Unauthorized delete
- Empty/invalid message content

---

### 🌍 📢 Global Message Module
**Features**
- Send a **global message** visible to all users
- View all global messages

---

### 7️⃣ 🔐 JWT Module
**Features**
- Generate and validate JWT tokens
- Extract username and expiration from token
- Filter requests to secure endpoints
- Handle unauthorized access

---

### 8️⃣ 📖 Swagger/OpenAPI Module
**Features**
- Automatically generate API documentation for all endpoints
- Interactive Swagger UI for testing APIs
- Supports JWT authentication in Swagger

---

## 🚀 Getting Started

### Prerequisites
- Java 21+
- Maven 3+
- MySQL running locally (or H2 for testing)