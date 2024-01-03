## Gym Task

#### This project uses MySql as a database, so it should be installed in order to work.

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

Or enable these properties in the 'props' bean in the JpaBeanConfiguration:

```props.put("hibernate.hbm2ddl.import_files", "trainingTypes.sql");```

```props.put("hibernate.hbm2ddl.auto", "create-drop");```

in order to allow automated creation of the tables based on the entity classes.


### Integration with Tomcat
In order to run this application, Tomcat 9 must be installed

* Run: ```./mvnw clean install``` in the terminal to generate the application war file in the target folder


Once Tomcat 9 is set up and running go to the webapps folder and copy the generated war file inside.
