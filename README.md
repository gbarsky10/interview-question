# Student Course Enrollment System  

### A RESTful API that manages student enrollments and course creations. 

## Run the program

### Executable JAR
```bash
$ git clone https://github.com/gbarsky10/interview-question.git
$ cd interview-question
$ mvn package -DskipTests
$ java -jar target/demo-<version>.jar
```

The Swagger UI is available at `http://localhost:8989/swagger-ui.html`.

## Tests
### Maven
* Run only unit tests:
```bash
$ mvn clean test
```

In order to test via Swagger UI add a new course request for example, expand the `POST` operation. Then click on the `Try it out`, add the payload below to the `Request Body` text area, and click on the `Execute`:
```json
{
  "title": "Math",
  "startDate": "2021-08-10",
  "endDate": "2021-08-15",
  "capacity": 2
}
```

### H2 Console
H2 console is available at `http://localhost:8989/h2-console`.

Fill the login form as follows and click on Connect:
* Saved Settings: **Generic H2 (Embedded)**
* Setting Name: **Generic H2 (Embedded)**
* Driver class: **org.h2.Driver**
* JDBC URL: **jdbc:h2:mem:testdb**
* User Name: **sa**
* Password:  **password** 

### PostMan Collection to run the operations
Import to a PostMan Student Course Enrollment.postman_collection.json to be able to 
execute operations via PostMan

