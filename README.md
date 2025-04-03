# KickerELO

KickerELO is a web application for displaying Elo ratings for foosball (table soccer) games.  
It uses **Spring Boot** for the backend, **Vaadin** for the frontend, and **MariaDB** as the database.

## Requirements

- **Java 23** or later
- **Maven** (if not integrated)
- **MariaDB** (for production use)

## Installation

### 1️⃣  Clone the repository
```sh
git clone https://github.com/your-repo/kickerelo.git
cd kickerelo
```

### 2️⃣  Set up the database

- **For testing**: No database setup is required, as an H2 in-memory database is used by default.
- **For production**: The application requires MariaDB. If MariaDB is already installed, make sure the database and
credentials are correctly configured in `application.properties`.

If you don't have MariaDB installed, you can quickly start a database using Docker:

```sh
docker run --name kickerelo-db -e MYSQL_ROOT_PASSWORT=root -e MYSQL_DATABASE=kickerelo -p 3306:3306 -d mariadb:latest
```

### 3️⃣  Build the project

To generate the file `target/kickerelo.jar`:

```sh
mvn clean package
```

### 4️⃣  Run the application

- For testing (default):

```sh
mvn spring-boot:run
```

- For production (requires MariaDB):

```sh
mvn spring-boot:run -Dspring-boot.run.profiles=production
```

Alternatively, you can run the built .jar file:

```sh
java -jar target/kickerelo.jar
```
