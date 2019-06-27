# cosmic-river

Minimalistic event-driven message oriented middleware  GitHub automation for bigger/small organisations or individuals

- [Rationale](#rationale)
- [quickstart](#quickstart)
- [Use-cases](#use-cases)
- [Specification](doc/specification.md)

# Rationale:

Most of the teams organisations use an automation around GitHub in terms of CI, Releases, Social-Network automation, etc.
Some use a polling mechanism and others might use GitHub web-hooks, this are provided by GitHub API.

Both methods have problems:

-  they are not universal and generic

-  In a polling method most of tools developers create a custom specific tool which fits only their specific problem, e.g getting a specific "event" in different languages, do the application code etc.

-  In Web-hook scenario tools devlopers mostly don't have rights to setup web-hooks in all repository. E.g you can't setup a web-hook to https://github.com/clojure/clojure or others repositories you haven't control.

Cosmic river has been designed with the aim of ensuring that it satisfies the requirements of both application developers as well as toolmakers.
Cosmic river by a simple configuration, you can watch all your GitHub repository of your choice and  where you don't have control, and automate your workflow based on GitHub.

Also tool/application developers will have an universal API on events, where they can bind their application without any development cost.


## Use-cases:

Note that by it's universality, cosmic-river has a huge variety of application. It can be applied in all domains where event-driven automation based on GitHub make sense.

As example consider this use-case:

- Release automation:
 * consider https://github.com/clojure/clojure publish a new release, cosmic-river server will notify the cosmic-river clients, which will perform actions  like trigger automatically a message to slack/irc, or a twitter message etc.
 * The slack/IRC notification is the application developers responsability to interface. (see the tutorial for more details)
  
 * consider  the https://github.com/kubernetes/kubernetes publish a new release, cosmic-river can trigger automatically the needed automation for making a new package for your distro, testing it with your tool

Other use-case are:

`Testing-automaiton`, `infrastructure-as-code`, `Data-Sciences`, `Packaging`, `Social-Networking automation`, everything based on a GItHub event. etc

# Architecture:

Cosmic River is a lightweight Distributed application Server/Client model.

The server application is composable as also the clients. 

The server component will listen on events of GitHub API3 and send them to the message broker of your choice. (rabbitmq and kafka (kafka not yet implemented)

The clients will then consume this events and with a message-handler and filter react on this event. 

An application/tool developer need only to implement the handler and the needed filter of events.

# Layout of repo:

This repository contains basically two clojure projects.

- cosmic-river (server)
- criver-client

Cosmic-river server aims to distribute the GitHub events to the message-brokers.

The criver-clients will consume these events, filtering them and triggering the automation needed.

# Quickstart

For a step by step tutorial, refert to FIXME.

# Community:

You can join #cosmic-river at https://clojurians.slack.com/

or open a thread at http://clojureverse.org/

or open an Issue here.

# Roadmap:

https://github.com/MalloZup/cosmic-river/projects/2

## License

Copyright © 2019 Dario Maiocchi
