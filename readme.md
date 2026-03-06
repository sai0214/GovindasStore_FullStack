Govindas Store
A professional Full-Stack E-Commerce Application built with a focus on security, scalability, and modern UI/UX. This project demonstrates a clean separation of concerns between a Spring Boot backend and a Vanilla JavaScript frontend.

Project Overview
This application was developed as part of a skill-upgrading journey to master Spring Security and JWT-based authentication. It features a robust inventory management system and a seamless shopping experience.

Key Features
Secure Authentication: Implements JWT (JSON Web Tokens) for stateless session management and OAuth2 (GitHub) for social login.

Role-Based Access Control: Separate interfaces and permissions for ADMIN (inventory management) and USER (shopping).

Dynamic Inventory: Real-time stock tracking with "Low Stock" and "Out of Stock" alerts.

Order Management: Complete flow from cart management to order history tracking.

Global Exception Handling: Structured API error responses for a better developer experience.

🛠️ Tech Stack
Backend: Java 17+, Spring Boot 3.x, Spring Data JPA, Spring Security.

Database: MySQL / PostgreSQL.

Frontend: HTML5, CSS3 (Modern Grid & Flexbox), JavaScript (ES6+).

Security: JWT, BCrypt Password Encoding, OAuth2.

Monitoring (Planned): Prometheus & Grafana for system health tracking.

Project Structure
Plaintext
GovindasStore_FullStack/
├── backend/            # Spring Boot Application
│   ├── src/            # Business logic, Security, and Controllers
│   └── pom.xml         # Maven dependencies
├── frontend/           # Static Web Files
│   ├── css/            # Premium Indigo/Slate styling
│   ├── js/             # Centralized API and Auth logic
│   └── *.html          # UI Pages (Store, Admin, Cart, etc.)
└── README.md

Local Setup
Clone the Repository:

Bash
git clone https://github.com/sai0214/GovindasStore_FullStack.git
Backend Configuration:

Update application.properties with your database credentials.

Run mvn spring-boot:run.

Frontend Configuration:

Update API_URL in js/app.js to http://localhost:8080/api.

Open index.html via Live Server.

Security Implementation
The project uses a custom JwtFilter to intercept requests, validate tokens, and set the security context. All passwords are encrypted using BCryptPasswordEncoder before being stored in the database.