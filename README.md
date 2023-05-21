# City application
CityApp is a web application built with Spring Boot and Angular. It allows users to browse a paginated list of cities, search for cities by name, and edit city names and photos.

## Prerequisites
Before running the project, make sure you have the following software installed:

1.  [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) or later (tested on Java 17)
2.  [Node.Js](https://nodejs.org/en) or later (tested on 18.16.0)

## Installation
1. Clone the project repository to your local machine:
```bash
git clone https://github.com/your-username/cityapp.git
```
2.Backend Setup:
- Open a terminal or command line at the root project folder.
- Run the following command to start the backend server (or open project and run in IntelliJ IDEA or other IDE):
```bash
./mvnw spring-boot:run
```
3. Frontend Setup:
- Install Angular CLI globally by running the following command:
```bash
npm install -g @angular/cli
```
- Open a terminal or command line at the "angular-cityapp" folder.
- Install all required libraries by running the following command:
```bash
npm install
```

## Run the Application
To run the CityApp, follow these steps:
Backend:
Make sure the backend server is already running as explained in the "Backend Setup" section.
Frontend:
Open a terminal or command line at the "angular-cityapp" folder.
Run the following command to start the frontend server and open the application in your default browser:
```bash
ng serve --open
```
The application will now be accessible in your browser at http://localhost:4200.

## License

This project is licensed under the [MIT License](LICENSE).

[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

## Author
[Mikhail Shepelev](https://github.com/mikhailshepelev)
