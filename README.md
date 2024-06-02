# WithoutNet Central Server

## About
This is the Central Server in the WithoutNet network model. 

It is responsible for storing information about WithoutNet networks and nodes, as well as undelivered messages, to facilitate their propagation.

## How to Build and Run
To build the WithoutNet Central Server, download its source code, unzip it and run `mvn clean compile`.

To run the WithoutNet Central Server, first run your database server and set its URL, username and password in the application.properties file.

Once this is done, simply run `mvn spring-boot:run`.