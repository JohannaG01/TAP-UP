# TAP-UP

## Local Execution

To run the application in your local environment, follow these steps:

### 1. Set Environment Variables

Before starting the application, make sure to define the following environment variables:

- **`POSTGRES_USER`**: The username for the database.
- **`POSTGRES_PASSWORD`**: The corresponding password for the database user.
- **`POSTGRES_DB`**: The name of the database to use.
- **`POSTGRES_PORT`**: The port on which the database is running.
- **`POSTGRES_HOST`**: The host address of the database.
- **`ENVIRONMENT`**: The environment in which the application is running (e.g., loca, dev, prod).
  
You can do this in several ways, but a simple option is to create a `.env` file in the same directory as your `docker-compose-local.yml`, with the following content:

```
POSTGRES_USER=your_username
POSTGRES_PASSWORD=your_password
POSTGRES_DB=your_db_name
POSTGRES_PORT=your_port
ENVIRONMENT=your_environment
```
### 2. Build the Application

Run the following command to build the application:
```
docker-compose -f docker-compose-local.yml build
```

### 2. Run Docker Compose

Navigate to the directory where the `docker-compose-local.yml` file is located and run the following command:

```
docker-compose -f docker-compose-local.yml up -d
```
