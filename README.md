# Project Setup Instructions

Please follow these steps to setup the project:

## Step 1: Database Configuration

Please configure your database in the `application.properties` file. Replace the parameters with your own database
settings:
spring.datasource.url=jdbc:postgresql://localhost:5000/wide
spring.datasource.username=root
spring.datasource.password=password

## Step 2: Application Start

You can either start the application by running `com.widetech.menuapp.MenuappApplication` class directly, or deploy it
as a jar package.

## Step 3: API Inspection

Once the application is running, you can check the RESTful API using Swagger. Access the following URL from your
browser:
http http://{your server address}:8080/swagger-ui/index.html#/