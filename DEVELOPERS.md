# User API Dev Guide
This API is designed as per the Business logic given in README.md for managing USERS and ACCOUNT.
## Building
The project is basically using MySQL as database. There are different layers in which whole API is distributed. Following are the details:
1) Entity Package:
	The user Entity and account entity are basic classes with Entity annotation. They have same fields as we require in our DB.
	
2) Repository Package:
	This package has 2 repository as entity which extends JPARepository for all JPA functionality.
	
3) Service Package:
    It has a Service Interface where all the methods are declared. 
    It has a service implementation class which implements the interface where all the business logic is deployed. 
    If the difference between the salary and expense is less than $1000, then that user is not allowed to create and account.

4) Controller Package:
	This Package has the HTTP requests for create, fetch all users, fetch user by email and delete user by email in a controller class.
	It also has a controller method to implement the account creation as per the provided business logic.

5) Custom Exception Package:
	This package has all the required custom exception method to be used for required exceptions.	
	
## Testing
The API is unit tested for all layers following the BDD (Behaviour Driven Development). 
The service layer is unit tested with MOCKITO API, where the repository layer(dependency) is mocked and stubbed to test the service layer methods.
The Controller layer is unit tested with Spring Frameworks Web Mvc Test along with Hamcrest for case matching and Jackson API,
	where all the service layer dependencies are mocked and stubbed to test the controller layer.
The Repository layer is unit tested with Data JPA test and AssertJ Core API.
In all above layers both positive and negative test scenarios can be tested with proper status codes and error messages.

Integration test has also been integrated using Spring Boot Test and configured DB is MYSQL. In some scenarios both Positive and Negative test is also tested.
Integration Test is also tested on the TestContainers using DOCKER. TestContainers is implemented in containerControllerIT.java

## Deploying
The API is connected with MYSQL Database. By default, Spring provides apache tomcat server to host the DB. The API is build with context path "/api/v1".

## Additional Information
The DB URL, USERNAME and PASSWORD needs to be configured with the correct url, username and password before using the API on any local machine in the
	"application.properties" file in the "resources" folder. Also create the Database in MYSQL, the tables and DB fields will be 
	auto created when the API is started.