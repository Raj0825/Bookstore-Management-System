# 📚 Cloud-Native Bookstore Management System
**Full-Stack E-commerce Backend | Spring Boot & Microservices-Ready Architecture**

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=Spring%20Security&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)

---

## 🚀 Project Overview
This is a production-grade bookstore management system designed to handle secure transactions, inventory tracking, and personalized user experiences. Built with a focus on **Stateless Authentication** and **Complex Database Queries**, it demonstrates a "not just on localhost" approach to software engineering.

### 🔑 Key Features
- **Stateless Security:** Implemented **JWT (JSON Web Tokens)** for secure, scalable authentication.
- **Role-Based Access Control:** Distinct workflows for `Admin` (Inventory Management) and `User` (Browsing & Checkout).
- **Smart Recommendations:** A content-based engine using complex SQL subqueries to suggest books based on user history.
- **Inventory Sync:** Real-time stock updates during the multi-step checkout process.

---

## 🛠 Technical Architecture


- **Backend:** Java 17, Spring Boot 3.0
- **Data Layer:** Spring Data JPA, Hibernate, MySQL (Aiven Cloud)
- **Security:** Spring Security, BCrypt Password Encoding
- **Infrastructure:** Maven, Docker

---

## ⚙️ How to Run Locally

### Prerequisites
- JDK 17+
- Maven 3.6+
- MySQL Server

### Steps
1. **Clone the repository:**
   ```bash
   git clone [https://github.com/Raj0825/Bookstore.git](https://github.com/Raj0825/Bookstore.git)
