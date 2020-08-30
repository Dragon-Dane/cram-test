# cram-test

## Requirements

For building and running the application you need:
- Java Version 11
- Maven 
## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `Application` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```
## Rest API Endpoints

###Task 1.2: 
Returns an image 
```shell
http://localhost:8080/api/v1/summary/image
```

###Task 1.3: 
Returns List of summary object with pagination
```shell
localhost:8080/api/v1/summary/fetch?fromDate=2020-08-10&ToDate=2020-08-14
```

###Task 2.2:  
Returns an image
```shell
http://localhost:8080/api/v1/summary/district/{bbsCode}
```