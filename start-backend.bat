@echo off
set DB_USERNAME=root
set /p DB_PASSWORD="Enter MySQL Root Password: "
echo Starting Spring Boot Backend on http://localhost:8080 ...
mvn spring-boot:run
