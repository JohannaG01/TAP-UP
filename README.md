# TAP-UP

## Tapup - Horse Betting Application
Tapup is a horse race betting platform that allows users to place bets, manage horse races, and process refunds. This project is built using Spring Boot and Java 17, leveraging modern web technologies and security best practices.

### Key Features
- **Horse Race Management:** Create, schedule, and cancel horse races.
- **Betting System:** Users can place bets on upcoming races and manage their bets.
- **Refund Processing:** Handle refunds automatically when a race is canceled.
- **User Management:** Users can register and log in.
- **Notifications:** Users receive real-time notifications about race updates, results, and betting outcomes.
- **Horse System:** Manage horses, including adding, updating, and tracking their race performance.
- **Payment Processing:** Secure payment gateway integration for deposits, bet placements, and payouts.
- **Security:** Integrated with Spring Security for authentication and authorization.
- **Unit Tests:** Ensure application reliability and maintainability with comprehensive unit testing.
- **REST API Documentation:** Provides easy-to-use OpenAPI documentation for the endpoints.

### Dependencies
This project uses the following dependencies:

- **Spring Boot 3.3.4:**
  - `spring-boot-starter-web:` For building RESTful web services.
  - `spring-boot-starter-data-jpa:` For data persistence using JPA and Hibernate.
  - `spring-boot-starter-security:` To handle authentication and security.
  - `spring-boot-starter-actuator:` Provides operational information and health checks.
  - `spring-boot-starter-validation:` For validating user input in API requests.
  - `spring-boot-starter-hateoas:` To add HATEOAS (Hypermedia as the Engine of Application State) support.
- **PostgreSQL 42.7.4:** PostgreSQL driver for database integration.
- **JUnit 5.11.2:** For testing the application.
- **JWT (JSON Web Token) 0.12.6:** To handle authentication using JWT tokens.
- **ModelMapper 3.2.1:** For object mapping between DTOs and entities.
- **Lombok 1.18.34:** To reduce boilerplate code, such as getters, setters, and constructors.
- **SpotBugs 4.8.6:** To catch bugs and improve code quality.
- **Springdoc OpenAPI 2.6.0:** To generate OpenAPI documentation for your REST APIs.
  Prerequisites
  Java 17+
  Maven 3.6+
  PostgreSQL database

### Prerequisites
- Java 17+
- Maven 3.6+

## Local Execution (With Docker)
To run the application in your local environment, follow these steps:

### 1. Clone the repository

```
git clone https://github.com/JohannaG01/TAP-UP.git
```

### 2. Set Environment Variables

Before starting the application, make sure to define the following environment variables:

- **`POSTGRES_USER`**: The username for the database.
- **`POSTGRES_PASSWORD`**: The corresponding password for the database user.
- **`POSTGRES_DB`**: The name of the database to use.
- **`POSTGRES_PORT`**: The port on which the database is running.
- **`POSTGRES_HOST`**: The host address of the database.
- **`JWT_SECRET_WORD`**: The secret word in order to create JWT Tokens.
- **`ENVIRONMENT`**: The environment in which the application is running (e.g., loca, dev, prod).

You can do this in several ways, but a simple option is to create a `.env` file in the same directory as your
`docker-compose-local.yml`, with the following content:

```
POSTGRES_USER=your_username
POSTGRES_PASSWORD=your_password
POSTGRES_DB=your_db_name
POSTGRES_PORT=your_port
JWT_SECRET_WORD=your_secret_word
ENVIRONMENT=your_environment
```

### 3. Navigate to the Docker Compose Directory
   First, navigate to the directory where the docker-compose-local.yml file is located. Run the following command:

```
cd path/to/your/docker-compose-directory
```

### 4. Build the Application

Run the following command to build the application:

```
docker-compose -f docker-compose-local.yml build
```

### 5. Run Docker Compose

Run the following command to run de application:

```
docker-compose -f docker-compose-local.yml up -d
```

### 6. Create Admin User

To initialize your database with an admin user, you can execute the following SQL script. This script will create a new
user with the ID of 1, ensuring that the user has administrative privileges. Remember to replace `hashed_password` for
the corresponding value.

```
-- Drop NOT NULL constraints temporarily
ALTER TABLE users
    ALTER COLUMN created_by DROP NOT NULL;

ALTER TABLE users
    ALTER COLUMN updated_by DROP NOT NULL;

-- Insert the admin user into the database without specifying the id
INSERT INTO users (uuid, email, name, last_name, is_admin, balance, hashed_password, created_at, created_by, updated_at,
                   updated_by)
VALUES (gen_random_uuid(), 'email@email.com', 'system_admin', 'system_admin', true, 0, 'hashed_password',
        NOW(), NULL, NOW(), NULL);

-- Update created_by and updated_by columns with the newly generated id
UPDATE users
SET created_by = id,
    updated_by = id
WHERE id = (SELECT MAX(id) FROM users);

-- Reinstate NOT NULL constraints
ALTER TABLE users
    ALTER COLUMN created_by SET NOT NULL;

ALTER TABLE users
    ALTER COLUMN updated_by SET NOT NULL;

```

## Local Execution (Without Docker)
If you prefer to run your application locally without using Docker, you can follow these steps instead of step 5:

1. **Start the Database Service:** First, ensure the database service is running with the following command:

```
docker-compose -f docker-compose-local.yml up -d db
```

2. **Run the Application:** After the database is running, execute your application with:

```
mvn spring-boot:run
```

###### Note: The .env file is not automatically loaded when using mvn spring-boot:run. To ensure that your application recognizes the environment variables defined in the .env file, you can either export them manually in your terminal before running the command or use a library like dotenv to load them programmatically.