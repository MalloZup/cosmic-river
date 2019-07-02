# cosmic-river

Minimalistic event-driven message oriented middleware  GitHub automation for bigger/small organisations or individuals

- [quickstart](doc/quickstart.md)
- [architecture](doc/architecture.md)
- [real-examples](doc/real-examples.md)
- [Specification](doc/specification.md)
- [Rationale](#rationale)
# Rationale:

Most of the teams organisations use an automation around GitHub in terms of CI, Releases, Social-Network automation, etc.
Some use a polling mechanism and others might use GitHub web-hooks, this are provided by GitHub API.

Both methods have problems:

-  they are not universal and generic

-  In a polling method most of tools developers create a custom specific tool which fits only their specific problem, e.g getting a specific "event" in different languages, do the application code etc.

-  In Web-hook scenario tools devlopers mostly don't have rights to setup web-hooks in all repository. E.g you can't setup a web-hook to https://github.com/clojure/clojure or others repositories you haven't control.

Cosmic river has been designed with the aim of ensuring that it satisfies the requirements of both application developers as well as toolmakers.
Cosmic-river will collect all the GitHub events and dispatch them to specific clients.
The clients can react by the events with specific handlers, and or filter this events by categories.  

Also tool/application developers will have an universal API on events, where they can bind their application without any development cost.

Note that by by its universality, cosmic-river can be applied in different domains. 
It can be applied in all domains where event-driven automation based on GitHub make sense.

As example consider this use-case:

- Release automation:
 * you can watch a specific issue beeing closed, cosmic-river server will notify the cosmic-river clients, which will perform custom actions.

 * consider  the https://github.com/kubernetes/kubernetes publish a new release, cosmic-river can trigger automatically the needed automation for making a new package for your distro, testing it with your tool

- Testing, CI, Automation:
 * automation on  Pull-Requests, etc.

- Event Notifications:
 * get notified with an Issue is closed, or any other particular event occurs.

- Team Metrics/Events:
 * you could trigger specific actions/workflows, or simply gather data thanks to specifics events

### Layout of repo:

This repository contains basically two clojure projects.

- cosmic-river (server)
- criver-client

Cosmic-river server aims to distribute the GitHub events to the message-brokers.

The criver-clients will consume these events, filtering them and triggering the automation needed.

# Community:

You can join #cosmic-river at https://clojurians.slack.com/

or open a thread at http://clojureverse.org/,

or feel free to open an issue here for technical details.


# Roadmap:

https://github.com/MalloZup/cosmic-river/projects/2

## License

Copyright © 2019 Dario Maiocchi
