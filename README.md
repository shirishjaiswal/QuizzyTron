# **About QuizzyTron**
## **Name :-** QuizzyTron
## **Framework :-** Spring
## **Language :-** Java
## **Database** :- MySQL
## **Status** :- ***On-Going***

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
### **Installation**
1. Clone the repository: `git clone [https://github.com/shirishjaiswal/QuizzyTron]` 
2. Build the project: `mvn clean install`

go to : http://localhost:8082

### Screenshots
URL : ```http://localhost:8082/```
![image](https://github.com/shirishjaiswal/QuizzyTron/assets/98471211/4b534632-0f6d-499b-9a20-bf6fe35e393f)

URL : ```http://localhost:8082/signup```
![image](https://github.com/shirishjaiswal/QuizzyTron/assets/98471211/1e070d9e-94c3-4f0a-8342-38ddde728de4)

URL : ```http://localhost:8082/login```
![image](https://github.com/shirishjaiswal/QuizzyTron/assets/98471211/f20c08ee-9947-4858-b03a-8cb8dd2399d4)

Other end points needs to be integrated with frontend
![image](https://github.com/shirishjaiswal/QuizzyTron/assets/98471211/8c3520d0-94e3-40bc-9663-d8d2283b1431)
