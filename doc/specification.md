# Specification of cosmic-river

- [server](#server)
- [client](#client) 

# Server

In order to launch the server create a `criver-config.edn` file from where you execute the cosmic-river binary.


This is the minimal configuration, supported currenlty. ( other configuration will listed in future).

```
{:criver-config {
             ;; full-repo-name: owner/repo-name,  events: this are the events which you are interested for
             ;; https://developer.github.com/v3/activity/events/

             ;; this events are targeting a repository. there are others for user/orgs which are interesting too. (later)
             :repository-events [
               {:full-repo-name "MalloZup/fullrocketmetal" :events["repository", "issue"]},
               {:full-repo-name "MalloZup/missile" :events["repository"]},
               {:full-repo-name "MalloZup/kubeojo" :events["repository"]},
             ],

            :message-broker {:type "rabbitmq" :config {
                                                       :exchange-name "branzino"}
```
### Github events
- repository-events is a list of repository which you want to watch.
- each entry of `repository-events` is composed by:
  * full-repo-name: Owner and repo. It should be a public repo. For private you need authentification.
  * events: there is only 2 types of events. 
              * issue events are not supported
              * repository events are supported


### Message-brokers

You can choose different types of message brokers.

Currenlty only `rabbitmq` is supported.

Each message-broker will have it's own specific configuration.

In future other message-brokers are planned to be supported.


### authentification

Optional: authentification for github user.

```
             :oauth-token <token>
```              


#### Rabbitmq

```
 :message-broker {:type "rabbitmq" :config {
                                                       :exchange-name "my-exchange-name"}
```

* exchange-name: important exchange-name should be the same as in the client, so you can pass events.


Exchanges receive messages that are sent by producers. Exchanges route messages to queues according to rules called bindings.
For more details refer to upstream rabbitmq documentation.

# Clients:

We support only rabbitmq client currenlty.

You should start your client with the following file in same directory where you client run: `cr-rabbitmq.edn`


## clients

## Rabbitmq cosmic-river clients

When starting a client you need that the exchange exists on server. 
The exchange name will then receive mgs and execute the handlers.
For the same exchange, you can bind different handlers, ash showed in example:

{ 
             :rabbitmq-consumers [
             ;; exchange-name: shuould exist on the server side otherwise we can't bind
             ;; handler-shell: this is shell command that it will execute when the client receive the message.

             ;; -- handlers: they can be either commands or custom binaries/scripts

               {:exchange-name "repository-events" :shell-command "/full-pathtomy/handler.py"},
               {:exchange-name "clojure-events" :shell-command "uptime"},
               {:exchange-name "repository-events" :shell-command "uptime"},
             ]

}
