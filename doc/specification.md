# Specification of cosmic-river

- [server](#server)
- 


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


## Rabbitmq-Client
;; cosmic-river client for rabbitmq

;; read upstream rabbitmq documentation for further details
{ 
             :rabbitmq-consumers [
             ;; qe-name: queue name of consumer
             ;; exchange-name: shuould exist on the server side otherwise we can't bind
             ;; handler-shell: this is shell command that it will execute when the client receive the message.

             ;; -- filter allow to filter events on type and other criteria
             
             ;; below are listed the current criteria:

             ;; --> type: specify which type you wish to filter. The types are the GitHub types see https://developer.github.com/v3/activity/events/types/
              
             ;; --

             ;; TODO: implement a filter based on the events. E.g if we have a whole repository events, we should 
             ;; have the possibility to filter like only a released event. ( to be thinked about if we want to expose this or not)
               {:qe-name "branzi" :exchange-name "branzino" :shell-command "uptime" :filter {:type "CreateEvent"} },
               {:qe-name "branzi" :exchange-name "branzino" :shell-command "ls"},
             ]

}


