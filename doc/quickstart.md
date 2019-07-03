# Quickstart

In this tutorial we will use the released Jars. But you can build your own with `lein` the Clojure build tool.

Experimental documentation! 

# Cosmic-river Rabbitmq quickstart

Use cosmic-river server where the rabbitmq-server is running. In future a url can be passed to run cosmic-river in other machines separately from rabbitmq.

Given other times constraints, this is not yet implemented.

## Requirements:

- rabbitmq-server up and running. See upstream documentation for installation and setup: https://www.rabbitmq.com/download.html

## Server side:

* Create configuration:

The cosmic river server should need to be initialized with the respective configuration. `criver-config.edn`

Refer to [criver-config.example.edn](../criver-config.example.edn) for a minimal configuration.

You can add your N repository which send the event to exchange-name. An exachange-name can have different repos event. ( Refer to upstream doc of rabbitmq for more info).

* Initialize server:

After the config creation, initialize the server with `java -jar /home/dmaiocchi/bin/cosmic-river/target/cosmic-river-0.1.0-standalone.jar init`)

* Initialize the clients:

Before starting the server daemon initialize the clients so that you will receive the events.

## Client/s:

0) You should have initialized the server with init.

1) Create a config file: [cr-rabbitmq.edn](../criver-client/cr-rabbitmq.edn), where you specify the exchange-name and the handler.

2) Start the client with

```java -jar target/criver-client-0.1.0-standalone.jar```

3) You can now start the server which will send events if any or new.

___

# Server side

* Start the server

Start server with `java -jar /home/dmaiocchi/bin/cosmic-river/target/cosmic-river-0.1.0-standalone.jar start`
