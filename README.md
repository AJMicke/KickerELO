# KickerELO

KickerELO is a web application for displaying Elo ratings for foosball (table soccer) games.  
It uses **Spring Boot** for the backend, **Vaadin** for the frontend, and **MariaDB** as the database. It is compatible with any OpenID Connect (OIDC) provider.

## Requirements

- **Java 21** or later
- **Maven**
- **MariaDB** (for production use)

## Installation

### Clone the repository
```sh
git clone https://github.com/your-repo/kickerelo.git
cd kickerelo
```

If you want to run the application in production mode, you can skip to [Production](#production) and set up the database.


### Testing

You can run the application in test mode without setting up a database or authentication. In this mode, any page of
the app is available to any user without a login.

The result data will be stored in an in-memory H2 database. You can change the test data by modifying the `data.sql` file
or just delete it for a clean slate.

To start the application in test mode, run:

```sh
mvn spring-boot:run -Ptest
```

The app can then be accessed at `http://localhost:8080`.


### Production

In production mode, the application requires an external database to store the data. If MariaDB is already installed, make sure the
credentials are correctly configured in `application-prod.properties` and the database conforms to the schema given in `schema.sql`.

#### Set up the database

If you don't have a database, you can quickly start one using Docker and update its schema using the provided `schema.sql` file:

```sh
docker run --name kickerelo-db -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=kickerelo -p 3306:3306 -d mariadb:latest
docker exec -i kickerelo-db mysql -u root -p kickerelo < schema.sql
```

#### Set up authentication
In order for the application to start up in production mode, an OIDC provider must be configured in `application-prod.properties`.
Some pages of the app are inaccessible without a login.

#### Build the project

To generate the file `kickerelo.jar` target, run:

```sh
mvn package -Pproduction
```

#### Run the application

You can run the application in two ways:

1. Directly using Maven:

```sh
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

2. Using the .jar file:

```sh
java -jar target/kickerelo.jar --spring.profiles.active=prod
```

## License
This project is licensed under the terms of the Do What The F*ck You Want To Public License. See [LICENSE](LICENSE) for more details.
