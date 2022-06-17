# USERGUIDE #


The application is based on a java springboot backend and a react frontend.


### Requirements: ###
- Docker installation
- Oracle Open Jdk 15
- Apache maven


### Steps to run the application: ###

To run the application, the following steps have to be executed:

To create the binary files navigate to the directory "backend" 
* Execute the maven commands "clean" and "install" to create the binary files
  * If Intellij is installed: Run anything (Shortcut STRG + STRG -> mvn clean install)
  * or use command line prompt -> mvn clean install

To build and run all microservices do the following:
* Cd to the directory "backend" 
* Execute in the terminal: docker-compose up --build
* Afterwards open a browser and navigate to localhost:8080


### Note: ###

   * When running the container, make sure there aren`t any other containers running, which might disturb the execution
   * The application is tested within the browser Chrome
   * The react deployment files are already added to the advertisement service and need no further adjustment.
   * In the directory "video" are videos showing how to application can be used