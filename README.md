# shopping-cart
Technical assignment - Back-end


This Project is an application skeleton for based on Technical assignment by using Java and Spring Boot 
You can find the relavant React Js frontend on GitHub https://github.com/rowi-dev/shopping-cart-frontend
 
## Getting Started

### Prerequisites

* Git
* JDK 8 or later
* Maven 3.0 or later


### Clone
To get started you can simply clone this repository using git:
```
git clone https://github.com/rowi-dev/shopping-cart
cd shopping-cart

```


### Configuration
In order to get your application working you have to provide the following settings:
```
* <property name="LOG_HOME" value="{YOUR_LOG_PATH}"/>

The configuration is located in `src/resources/spring-logback.xml`.

* spring.profiles.active={YOUR-PROFILE}

The configuration is located in `src/resources/bootstrap.properties`. (Not required, You can use default configs by updating the relevant value)

* spring:
  datasource:
    url: jdbc:mysql://localhost:3306/{YOUR_DB}?createDatabaseIfNotExist=true&autoReconnect=true&useSSL=false
    username: {DB_USER_NAME}
    password: {DB_PASSWORD}

The configuration is located in `src/resources/application-{YOUR_PROFILE}.yml`.

```


### Build an executable JAR
You can run the application from the command line using:
```
mvn spring-boot:run
```
Or you can build a single executable JAR file that contains all the necessary dependencies, classes, and resources with:
```
mvn clean package
```
Then you can run the JAR file with:
```
java -jar target/*.jar
```


*Instead of `mvn` you can also use the maven-wrapper `./mvnw` to ensure you have everything necessary to run the Maven build.*


##End

