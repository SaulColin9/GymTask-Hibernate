## Gym Task

#### This project uses MySql as a database, so it should be installed in order to work.
### This application uses ActiveMQ as a message broker for communication between microservices, so make sure to install it and set it up and running

#### Go to the storage.properties file stored inside:
* src/main/resources/storage.properties


[//]: # (#### Make sure to change the port, hostname, username and password variables to the ones needed for your machine)

#### Inside that file there will be the following properties:
* port
* hostname
* password
* username

#### so make sure to change them in order to meet the values for your machine.
#### By default, port will have a value of 3306 and hostname will have localhost.

### Steps to run the schemas:
In the resources folder under the path:
* src/main/resources/ddl.sql

there will be a file containing the ddl script to create the trainee, trainer, training, trainingType and user tables;
it also creates the default insert values for the training types table.

### Profiles
There are four different profiles:
* dev
* local
* stg
* prod

in order to select each one go to application.properties and set the 
```spring.profiles.active=jpa,<profile>``` profile to the one you need.

### Start application
Go to GymApplication class and execute the main method in order to start the spring boot application.
