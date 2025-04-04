# KickerELO

KickerELO is a web application for displaying Elo ratings for foosball (table soccer) games.  
It uses **Spring Boot** for the backend, **Vaadin** for the frontend, and **MariaDB** as the database.

## Requirements

- **Java 23** or later
- **Maven** (if not integrated)
- **MariaDB** (for production use)

## Installation

### Clone the repository
```sh
git clone https://github.com/your-repo/kickerelo.git
cd kickerelo
```

If you want to run the application in production mode, you can skip to [Production](#production) and set up the database.


### Testing

To run the application in a test environment, you can use an embedded H2 database. This is useful for development and testing purposes.

To build the project and run the application with the embedded H2 database, use the following commands:

```sh
mvn clean package
mvn spring-boot:run
```


### Production

The application requires a database to store the data. If MariaDB is already installed, make sure the database and
credentials are correctly configured in `application-prod.properties` and skip to step [Build the project](#build-the-project).

#### Set up database

You can quickly start a database using Docker and update its schema using the provided `update-schema.sql` file.

```sh
docker run --name kickerelo-db -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=kickerelo -p 3306:3306 -d mariadb:latest
docker exec -i kickerelo-db mysql -u root -p kickerelo < update-schema.sql
```


#### Build the project

To generate the file `target/kickerelo.jar`:

```sh
mvn clean package -Pproduction
```

### Run the application

You can run the application in two ways:

1. Using Maven:

```sh
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

2. Using the built .jar file:

```sh
java -jar target/kickerelo.jar --spring.profiles.active=prod
```
