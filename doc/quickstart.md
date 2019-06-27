# Quickstart

In this tutorial we will use the released Jars. But you can build your own with `lein` the Clojure build tool.

# Cosmic-river Rabbitmq quickstart

## Requirements:

- rabbitmq-server up and running.

## Server side:

The cosmic river  server should need to be initialized with the respective configuration. `criver-config.edn`

Create the following file
```
{:criver-config {
             :repository-events [
               {:full-repo-name "MalloZup/fullrocketmetal" :events["repository"]},
               {:full-repo-name "MalloZup/missile" :events["repository"]},
               {:full-repo-name "MalloZup/kubeojo" :events["repository"]},
             ],

            :message-broker {:type "rabbitmq" :config {
                                                       :exchange-name "branzino"}
                            }
}}
```

This configuration will watch 3 repository for repository events type. 
We have  choosed the rabbitmq as message-broker/

Start the server with the jar. 


## Client sides:

The clients have specific configuration too. You can have as many client as you wish and the same for the servers.
