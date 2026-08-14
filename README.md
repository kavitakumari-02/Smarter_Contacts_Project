 Smart Contact Manager

Smart Contact Manager is a web-based contact management application developed using Java and Spring Boot. 
It allows users to securely manage their contacts, including adding, viewing, updating, and deleting contact information.

**Features**

- User registration and login
- Secure authentication and authorization using Spring Security
- OAuth2 / Google login
- User-specific contact management
- Add new contacts
- View all contacts
- Update contact details
- Delete contacts
- Upload and display contact profile images
- Setting feature
- Favorite contacts
- Contact pagination
- User dashboard
- Responsive user interface
- Logout functionality
- Error and exception handling

## Technologies Used

### Backend
- Java
- Spring Boot
- Spring MVC
- Spring Security
- Spring Data JPA
- Hibernate
- REST concepts

### Frontend
- HTML
- CSS
- Bootstrap
- Thymeleaf
- Font Awesome
- TinyMCE

### Database
- MySQL

### Tools
- Maven
- Eclipse / Spring Tool Suite
- Git
- GitHub

## Project Structure

```text
Smart-Contact-Manager
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.springboot.project1
│   │   │       ├── controller
│   │   │       ├── repository   
│   │   │       ├── model
│   │   │       ├── security
│   │   │       └── exception
│   │   │      
│   │   │
│   │   └── resources
│   │       ├── templates
│   │       ├── static
│   │       └── application.properties
│   │
│   └── test
│
├── pom.xml
└── README.md

## Main Modules

**Authentication and Authorization**

Spring Security is used to protect the application. Users must authenticate before accessing protected contact-management features.

The application also supports role-based authorization, where access can be controlled based on the user's role.

**Contact Management**

Authenticated users can:

Add contacts
View contacts
Update contacts
Delete contacts
Upload contact images
View individual contact details

**Pagination**
Contacts are displayed using pagination so that a large number of contacts can be handled efficiently.

**Image Upload**
Users can upload a profile image while creating a contact. The uploaded image is stored and displayed with the contact information.

**Database**
The application uses MySQL as the database and Spring Data JPA/Hibernate for database operations.

**Example entities include:**
User
Contact

A user can have multiple contacts.
   # Author
     Kavita kumari
