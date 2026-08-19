# Gym Management System Developer Guide

## Architecture Overview

The Gym Management System was created in Java and uses PostgreSQL to store the application's data. The project was separated into different classes so that each part of the application has its own responsibility.

The general structure of the application is:

**Console UI → Service Layer → DAO Layer → PostgreSQL Database**

The console is used to interact with the user. The service classes handle the main application logic, and the DAO classes are responsible for communicating with the PostgreSQL database.

This structure helped us keep the database code separate from the rest of the program and made the project easier to organize between group members.

## Class Design

The project contains classes for the main parts of the gym system, including users, memberships, workout classes, and merchandise.

For features that interact with the database, DAO classes are used to handle database operations. Service classes are then used between the DAO and the rest of the application.

For example, the membership section contains:

* `Membership` - stores information about a membership.
* `MembershipDAO` - handles membership information in PostgreSQL.
* `MembershipService` - handles the membership functions used by the application.

The other areas of the application follow a similar structure where needed.

## Database Design

The application uses a local PostgreSQL database and connects to it through JDBC.

The main database tables include:

* **Users** - stores account information, contact information, passwords, and user roles.
* **Memberships** - stores membership information, prices, and the user connected to the membership.
* **WorkoutClasses** - stores information about workout classes and the Trainer connected to each class.
* **GymMerch** - stores merchandise information including the product name, type, price, and stock.

Primary keys are used to uniquely identify records in each table. Foreign keys are used where tables need to be connected. For example, a membership is connected to a user through the user's ID.

## Setup Instructions

To run the project:

1. Clone the GitHub repository.
2. Open the project in VS Code or another Java IDE.
3. Make sure Java JDK 17 or higher is installed.
4. Install and configure PostgreSQL.
5. Run the provided SQL setup script to create the required database tables.
6. Configure the database credentials used by the Java application.
7. Make sure all required dependencies are installed.
8. Build the project using Maven if Maven is being used.
9. Run the application's main Java file.

## Dependencies

The project uses the PostgreSQL JDBC driver so the Java application can connect to PostgreSQL.

BCrypt is also used for password security. User passwords are hashed before they are stored rather than storing the original password.

If Maven is used, these dependencies are managed through the project's `pom.xml` file.

## Logging

The application uses logging to keep a record of important events that happen while the program is running.

The application logs events such as:

* System startups
* Failed login attempts
* Database transaction errors
* Admin overrides

The logs are written to an `app.log` file. Using a log file instead of only printing information to the console makes it possible to look back at errors and important events after the program has finished running.
