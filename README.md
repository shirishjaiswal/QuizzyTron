# **About QuizzyTron**
## **Name :-** QuizzyTron
## **Framework :-** Spring
## **Language :-** Java
## **Database** :- SQL
## **Status** :- ***On-Going***

## Features
- User registration and account creation
- Quiz attendance and performance tracking
- Quiz history and score tracking
- User-friendly interface

## **Application Properties**
```
Server.port = 8082
spring.datasource.driver-class-name = com.mysql.cj.jdbc.Driver
spring.datasource.url = jdbc:mysql://localhost:3306/examportal?createDatabaseIfNotExist=true
spring.datasource.username = root
spring.datasource.password = root
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto = update
spring.jpa.show-sql = true
spring.jpa.properties.hibernate.format_sql = true
spring.main.allow-circular-references=true
```

## Requirements
1. IDE (Intellij Idea / Eclipse)
2. Web Browser (Chrome / Brave)
3. SQL Database

## Installation
To run QuizzyTron on your local machine, follow these steps:

1. Clone the repository: `git clone https://github.com/shirishjaiswal/QuizzyTron`
2. Navigate to the project directory: `cd QuizzyTron`
3. Install dependencies: `mvn install`
4. Run the application: `mvn spring-boot:run`

## Usage
Open your web browser and go to `http://localhost:8082` to access QuizzyTron.

### Screenshots
Home URL : ```http://localhost:8082/```
![image](https://github.com/shirishjaiswal/QuizzyTron/assets/98471211/07547c65-7731-42b4-82e7-712c632f990c)

Endpoints remained to integrate with FrontEnd
![image](https://github.com/shirishjaiswal/QuizzyTron/assets/98471211/8c10b77b-a659-4303-8d98-79aa7689fd39)

## Acknowledgements
Resources and libraries that have been instrumental in building QuizzyTron:
- Spring Boot: https://spring.io/projects/spring-boot
- Thymeleaf: https://www.thymeleaf.org/


GitHub: ```https://github.com/shirishjaiswal```
LinkedIn: ```https://www.linkedin.com/in/shirish-jaiswal/```
