🍽️ Online Food Ordering System (Java + MySQL)

A console-based Online Food Ordering System developed using Core Java, JDBC, and MySQL.
This project demonstrates CRUD operations, database relationships, and real-world ordering workflow suitable for academic projects and resumes.

📌 Features

👤 User Management
User registration
Role-based user (Admin / Customer)
User details stored securely in MySQL

📋 Menu Management
View available food items
Menu fetched dynamically from database
Prices and availability handled via DB

🛒 Cart Management
Add items to cart
Update quantity if item already exists

View cart with:
Item name
Quantity
Price
Line total
Clear cart after successful order

📦 Order Management
Place order with delivery address
Payment methods supported:
COD
CARD
UPI
Order status tracking
Order items stored with price snapshot

🗄️ Database Design
Proper relational schema
Foreign key constraints
ON DELETE CASCADE for data integrity

🛠️ Technologies Used
Technology	Purpose
Java (Core)	-Application logic
JDBC	    -Database connectivity
MySQL    	-Backend database
OOP Concepts-Models & DAO design

🗂️ Project Structure
OnlineFoodOrderingSystem/
│
├── com/
│   ├── dao/
│   │   ├── UserDAO.java
│   │   ├── MenuDAO.java
│   │   ├── CartDAO.java
│   │   └── OrderDAO.java
│   │
│   ├── models/
│   │   ├── User.java
│   │   ├── MenuItem.java
│   │   ├── CartItem.java
│   │   ├── Order.java
│   │   └── OrderItem.java
│   │
│   └── utils/
│       └── DBConnection.java
│
├── Main.java
└── README.md

🧱 Database Schema
Database Name
FOOD
Tables
users
menu
cart_items
orders
order_items

✔ Proper primary keys
✔ Foreign key relationships
✔ Auto-increment IDs
✔ Referential integrity

⚙️ Setup Instructions
1️⃣ Create Database

Run the provided SQL script in MySQL:

CREATE DATABASE FOOD;
USE FOOD;
-- tables: users, menu, cart_items, orders, order_items


(Use the full SQL you already created)

2️⃣ Update Database Credentials

Edit DBConnection.java:

DriverManager.getConnection(
    "jdbc:mysql://localhost:3306/FOOD",
    "root",
    "your_password"
);

3️⃣ Add MySQL Connector

Add MySQL Connector/J to your project:
mysql-connector-j-8.x.x.jar

▶️ How the Application Works

User enters name, email, password, and role
User is stored in the database
Menu items are displayed
User adds items to cart
Cart can be viewed and updated
Order is placed with address and payment method
Order items saved and cart cleared

🧪 Sample Console Flow
===== Welcome to Online Ordering System =====
Enter Name: Abdul Saleem
Enter Email: saleem@gmail.com
Enter Password: ****
Enter true/false if you are admin or not: false

--- MAIN MENU ---
1. View Menu
2. Add Item to Cart
3. View Cart
4. Place Order
5. Exit

✅ Key Highlights

Clean DAO pattern
Strong OOP modeling
Proper foreign key usage
Ready for resume & GitHub
Easy to extend (GUI / Web / Spring Boot)

👨‍💻 Author

Abdul Saleem
B.Tech – Electrical & Electronics Engineering (AI & ML)
GitHub: https://github.com/CodeWithAbdulSaleem
LinkedIn: https://www.linkedin.com/in/abdulsaleem03