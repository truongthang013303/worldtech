# News Website
## IDE
		Spring Tool Suite

# Technologies:
-	Java 8
-	Spring Boot
-	Spring Data JPA
-	Spring Security
-	Thymeleaf
-	Bootstrap
-	JS
-	jQuery
-	MySQL
# Main Dependencies
- Spring Boot
- Spring Security
- Spring Data JPA
- Commonlang3
- Thymeleaf
- Thymeleaf Page Layout
- Thymeleaf Extra Security
- Spring Boot validation
- MySQL-connector
# How to use
### MySQL Configuration in 'src/main/resources/application.properties'
- spring.datasource.url=jdbc:mysql://localhost:3306/springboot

- spring.datasource.username=root

- spring.datasource.password=123456
### Database name
- Database name: springboot
### Create Database & Data of Tables
- Run this file in mysql: springboot20210801.sql
### The endpoints are located in 'http://localhost:8083/' and config its port on src/main/resources/application.properties
### Make sure to create a database called springboot
# Authority
- /quantri/** - [ADMIN] required
- Access to the admin page: /quantri
- Username: admin
- Password: 123456
# Link
### Admin Page Links
- /quantri/theloai/danhsach - category management page
- /quantri/baiviet/danhsach – new management page
- /quantri/nguoidung/danhsach – user management page
- /quantri/role/danhsach – role management page
### Home page Links
- / - home page
### Other Links
- /login - login page
- /logout - logout
- /register - register page



