# Dev-Connect

> This is my first real-world project — a **social network backend for developers** built with Spring Boot.

---

## 📖 About Dev-Connect
Dev-Connect is a backend application that enables developers to:
- Register and manage their profiles
- Post content
- Comment on posts
- Follow and unfollow other users
- View and interact with connections

It’s built as part of a **real-world learning journey** using Spring Boot and REST APIs.

---

## 🛠 Tech Stack
- **Java 21** (Spring Boot modules: Spring MVC, Spring Data JPA, Spring Security)
- **MySQL 8** or **H2** (Hibernate)
- **Maven**
- **Lombok** & **ModelMapper** (optional)
- **Git** for version control

---

## 📦 Modules

### 1️⃣ User Module
- Register a new user
- Update or delete own profile
- View other profiles by:
  - User ID
  - Username
  - Pagination & sorting for all users
- Security ensures users can only modify their own profile

### 2️⃣ Post Module
- Create, update, or delete own posts
- View posts of other users by username
- Pagination & sorting for posts
- Each post response also shows **comment count**

### 3️⃣ Connection (Follow) Module
- Follow and unfollow other users
- Cannot follow own account
- Prevents:
  - Following an already followed user
  - Unfollowing a user who is not followed

### 4️⃣ Comment Module
- Add comments to any post
- Delete comment if:
  - You wrote it, OR
  - It was written on **your own post**
- View all comments of a specific post
- **Admin** can delete any comment

---

## 🚀 Getting Started

### Prerequisites
- Java 21+
- Maven 3+
- MySQL running locally (or H2 for testing)

### Setup & Run
```bash
git clone https://github.com/Kpmax03/Dev-connect.git
cd Dev-connect
mvn spring-boot:run
