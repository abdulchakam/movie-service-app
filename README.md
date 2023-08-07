# Movie Service Application
Movie Service App is an application based on Spring Boot that allows you to manage a list of movies. 
This application provides an API to create, fetch, update and delete movies in the database.

# Main Feature 
  - Creates a new movie with information such as title, description, rating and image.
  - Retrieves a list of all available movies.
  - Retrieving movie details by ID.
  - Updates existing movie information.
  - Delete movies by ID.

# Spesification
  - Java 17
  - Maven 3.8.4
  - Swagger2
  - Postgresql
  - Framework Spring Boot
  
# Intructions
	- Clone the project
 	- Execute query DDL, DDL files are in the directory src/main/resources/DDL.sql
	- Open the project with the IntelliJ IDEA text editor and wait for the build process
	- Run the application in the main MovieServiceApplication method
	  Or type command in terminal mvn spring-boot:run, wait until the process is complete
	- After that open browser copy and paste this url http://localhost:8080/swagger-ui/index.html#/
