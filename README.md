# WjPG Building RESTful Services with Spring Example App

This application has the following features:

 * Modular architecture using Maven Modules
 * Uses H2 database (change this if you wish, the config files for that are in the webapp module)
 * Logback logging framework
 * TestNG (you can change this to JUnit if you like)
 * Environment for functional tests (directions below for running the functional tests)
 * Deployment via Cargo plugin (see: [http://cargo.codehaus.org/Maven2+plugin])


## Project Structure

The project is divided into following Maven modules:

* Core module
* Webapp module
* Functional Tests module

The core module is a place where all java code should go (business logic, controllers, entities etc.). However
it is a good practice to implement some modules, like clients to external services, as separate modules.

The webapp module is a place where all application configuration files are stored (``web.xml``, Spring, DB and
logger configuration).

The functional tests module is a place where, as a name says, functional tests go. It is my personal preference that instead of simulating container I like to run all functional tests in a real container. By default this module contains configuration for Jetty 6 and Tomcat 6 so you can run tests on those two containers. It is straight forward to add configuration for another container, check [Cargo plugin](http://cargo.codehaus.org/Maven2+plugin) for more details.

### Building the Project

Download the [latest Maven](http://maven.apache.org/download.html) and make it's bin directory accessible to your command line shell

To build a project navigate to parent module directory and type (top-level directory):

    $ mvn install

Your project will be compiled, packaged and installed into your local Maven repository. If you do not want to install application on your local repository execute:

    $ mvn package

The target .war archive is placed in ``~/webapp/target/`` (the ``target`` directory of webapp module).

By default the command will build the core and webapp modules omitting the functional tests module.

### Running the Project

You have two options to quickly run the project on embedded Jetty or Tomcat containers. It is not required to manually configure container, however if you want the IDE to give you some support (like debugger) you will need to manually configure the IDE.

To run the project, first navigate to webapp module directory and execute:

    $ mvn jetty:run

If you rather prefer to run the application on Tomcat execute:

    $ mvn tomcat:run

### Functional Testing

The functional tests module allows you to run tests against the real application deployed on real container instead of simulating container in your unit tests.

While the functional testing is time consuming process functional tests are not run by default (during the normal application build). 

You have two options two run functional tests. You can run functional tests while building the project by executing following command from parent module directory:

    $ mvn install -Pfunctional-tests

Or you can build normally the application, navigate to functional tests module directory and execute:

    $ mvn verify

By default functional tests are executed on Jetty 6 container. If you rather want to use Tomcat execute one of the following:

    $ mvn install -Pfunctional-tests,tomcat6x

or

    $ mvn verify -Ptomcat6x 

Note that in order to run functional tests you need to have your application installed in local repository.

## Debugging in your IDE

### Eclipse

At the top level folder type:

    $ mvn eclipse:eclipse

This command will instruct Maven to create Eclipse compatible project files for each module and point Eclipse's classpath to the dependencies referenced in the module's pom.xml file.

After you run this command, you can then go to File->Import in Eclipse and choose "Existing Project into Workspace".  Navigate to the top level folder of the project and select it.  You should see a graphic that looks like this: 

![Eclipse Import](http://wjpg.ca/files/nov2011/Eclipse-Import.jpg)

If you know how to configure your IDE to run your web applications against one of its embedded web application containers then that is a viable option. However, if you don't, then here is the workflow you'll want to use:

### Startup the Services
1. Make edits to your source files in Eclipse
2. From the command-line issue the following command:
    $ mvn clean install
This will build the core and webapp modules and install them into your local Maven repository.  The .war file should be placed under webapp/target
3. Change directories into the webapp folder
4. Set the following environment variable, and run the following command:

    ``$ export MAVEN_OPTS="-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=4000,server=y,suspend=y"``
On Windows:

    ``C:\> set MAVEN_OPTS="-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=4000,server=y,suspend=y"``

    ``$ mvn jetty:run``

5. If all goes well, the REST services are running at: ``http://localhost:8080/spring-rest/``, however because the ``MAVEN_OPTS`` include the ``"suspend=y"`` the services will not fully startup until you attach a debugger (see next step).  You can change this setting to ``"n"`` if you desire, typically you only use ``"y"`` if you need to debug something that is being bootstrapped by Spring when the application is starting.

### Attach the Eclipse Debugger

1. With the project files loaded into your workspace, go to Run->Debug Configurations...
1. Name: Debug REST Services
1. Choose "Remote Java Application" on the left hand side, and click the "New" button on the top left which looks like a piece of paper with a + sign on it.
1. Choose the "spring-rest-core" project by selecting it after pressing the Browse button.
1. Set the "Connection Type" to "Standard (Socket Attach)"
1. Host: localhost
1. Port: 4000
1. Click the Debug button
1. Too add a user: http://localhost:8080/spring-rest/users?firstName=Joe&lastName=Schmoe
1. Put some break points in the UsersController file and try invoking the services using a browser or a tool like curl (i.e. http://localhost:8080/spring-rest/users/).

#IMPORTANT

Anytime you change a file in any of the modules you have to issue the following:
    ``$ mvn clean install``

In ``webapp/src/main/webapp/WEB-INF/conf/db.properties`` change the following property such that it points to a valid folder on your system:
    ``db.url=jdbc:h2:*/tmp/*wjpg-app-db``

For instance, ``/tmp`` might not resolve to a valid folder on Windows.

