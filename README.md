# cosmic-river

Listen to GitHub V3 events and dispatch them via a message-broker of choice.

# Rationale:

Having event-driven automation: react to GitHub events and automate your organisation workflows with a universal simplicity.

# Layout of repo:

This repository contains basically two clojure projects.

- cosmic-river (server)
- criver-client

Cosmic-river server aims to distribute the GitHub events to the criver-clients

The criver-clients will consume these events, filtering them and triggering the automation needed.

# Roadmap:

See if a repo release and trigger event ( chat, etc)

(server-side): 
- implement message broker functionalities

(client-side)
filter the events of repo


## Usage

FIXME

## License

Copyright © 2019 Dario Maiocchi
