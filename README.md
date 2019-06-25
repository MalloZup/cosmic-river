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

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
