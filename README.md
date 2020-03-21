# spring-boot-project-with-h2-db
Spring boot project with H2 in memory database, performing create, delete, get and update operations. 
Handling spring boot way global exception handling and Swagger UI for Rest End points.

Employee domain of data is chosen for this use case with fields being Id, First Name, Last Name and Email. Id is
unique identifier used to identify a particular employee.

Storing data onto a persistent file to restore it in case of application crash. (This feature can be removed if we
don't need. Added for system to be resilient.)

### Requirements :
Java <br>
Maven

### Access Swagger UI for testing Rest Url's
http://localhost:2500/swagger-ui.html

Usage of API's is shown in Swagger UI. (Payload information is given as well.)

Underlying server taken as Jetty in this project. If you want tomcat, comment the jetty dependency and remove 
tomcat exclusion tag from pom xml file.

### EmployeeResourceTest class added for Mocking the Employee Resource Api's.

#### If you want run specific test case method, use the below mvn command :
mvn test -Dtest=EmployeeResourceTest#testGetEmployee test

#### If u want run all test cases with in the Test class, use the below mvn command :
mvn test -Dtest=EmployeeResourceTest test

#### If u want run all the test classes, use the below mvn command :
mvn test or mvn clean install 

#### Skip all test cases using below command : 
mvn clean install -DskipTests=true

#### Starting the application : 
java -jar target/spring-boot-project-with-h2-db-0.0.1.jar

Once the service starts, navigate to Swagger UI with the link provided above. That will open UI with the 
details of endpoints, description etc.

On clicking employee-resource tab it opens up all available endpoints for employee. Click on an endpoint you want to test 
out then click on "Try it out" and "execute" the request. If the request needs parameters it shows there.

Once we enter the required information data will be fetched and displayed.
