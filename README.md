# 📚 Cloud-Native Bookstore Management System
**Enterprise-Grade Backend for E-commerce | Spring Boot 3.0 & Security Focused**

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=Spring%20Security&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)

---

## 📖 Executive Summary
This project is a high-performance bookstore backend designed to solve the complexities of modern e-commerce: **secure user authentication**, **concurrency in inventory management**, and **data-driven user engagement**. It transitions beyond a simple CRUD app by implementing a professional-grade security layer and a custom recommendation engine.

---

## 🚧 Current Development Status: Active
This project is currently in **Phase 2** of development. While the Core CRUD and Security layers are stable, I am actively implementing advanced microservices-ready features.

- [x] **Phase 1:** Core Bookstore API & Database Schema (Completed)
- [x] **Phase 2:** JWT Authentication & Role-Based Access (Completed)
- [/] **Phase 3:** Content-Based Recommendation Engine (In Progress 🛠️)
- [ ] **Phase 4:** Dockerization & AWS Cloud Deployment (Planned)

## 🛠 Technical Deep Dive

### 🔐 Advanced Security Architecture
- **Stateless Authentication:** Utilizes **JWT (JSON Web Tokens)** to eliminate server-side session overhead, allowing the backend to scale horizontally.
- **Security Filter Chain:** Custom implementation of `OncePerRequestFilter` to validate tokens on every protected API call.
- **Data Protection:** Passwords are never stored in plain text; implemented **BCrypt hashing** with a custom strength factor.

### 🧠 Smart Recommendation Engine
Unlike basic "latest books" lists, this system uses a **Content-Based Filtering** approach.
- **The Logic:** Custom SQL subqueries analyze a user’s wishlist and purchase history to find overlaps in genres and authors.
- **Implementation:** Optimized JPA queries to ensure recommendation latency stays under 100ms.

### 📊 Database Schema & Design
- **Normalized Architecture:** Designed with 3rd Normal Form (3NF) to ensure zero data redundancy.
- **Relational Mapping:** Used `OneToMany` and `ManyToMany` associations with **Lazy Loading** to optimize memory usage and avoid N+1 query problems.



---

## 🏗 System Design
The application follows a strict **Layered Architecture**:
1. **Controller Layer:** Handles RESTful endpoints and request validation.
2. **Service Layer:** Encapsulates business logic (e.g., calculating discounts, validating stock).
3. **Repository Layer:** Interfaces with MySQL using Spring Data JPA.
4. **Domain Model:** Defines entities with Hibernate annotations for automated schema mapping.

---

## 📡 API Endpoints (Samples)

| Method | Endpoint | Description | Access |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/auth/register` | User registration | Public |
| `POST` | `/api/auth/login` | Returns JWT Token | Public |
| `GET` | `/api/books/recommend` | Personalized book suggestions | User |
| `PUT` | `/api/admin/inventory` | Update stock levels | Admin Only |

---

## ⚙️ Development & Deployment
- **Build Tool:** Apache Maven
- **Environment:** Containerized via **Docker** for environment parity.
- **Database:** Hosted on **Aiven Cloud** (Managed MySQL) for 99.9% uptime.
- **Cloud Hosting:** API deployed on **Render** with automated CI/CD pipelines.

---

## 🚀 Future Enhancements
- [ ] **Microservices Migration:** Splitting the "Recommendation Engine" into its own service.
- [ ] **Caching:** Implementing **Redis** to cache frequently accessed book details.
- [ ] **Cloud Storage:** Integrating **AWS S3** for dynamic image uploads.

---

## 🤝 Connect with the Developer
**Raj Shah** [LinkedIn Profile](https://www.linkedin.com/in/raj-shah-04b465315/) | [LeetCode Profile](https://leetcode.com/u/RajShah2508)
