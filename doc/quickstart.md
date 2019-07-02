# Quickstart

In this tutorial we will use the released Jars. But you can build your own with `lein` the Clojure build tool.

# Cosmic-river Rabbitmq quickstart

## Requirements:

- rabbitmq-server up and running. See upstream documentation for installation and setup

## Server side:
* Create configuration:

The cosmic river  server should need to be initialized with the respective configuration. `criver-config.edn`
Refer to `criver-config.example.edn` for a minimal configuration.


* Initialize server
After the config creation, initialize the server with `cosmic-river --init`

* Initialize the clients:

Before starting the server daemon initialize the clients so that you will receive the events.

* Start the server

Start server with ```cosmic-river --start```



## Client/s:

* configuration:

Refer to `cr-rabbitmq.example.edn` for the rabbitmq client minimal example.

* handler templates:

For your handlers take look on handlers-template directory `/cosmic-river/criver-client/handlers-template`.

Additionally you can find real-examples on `/home/dmaiocchi/bin/cosmic-river/criver-client/real-examples`

* start clients with

`criver-client`



