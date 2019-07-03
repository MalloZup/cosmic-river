#! /usr/bin/python3

### ------------------------------------
####  what does this event-handler it does?: ####

# It print the type of all repository events we receive

import os

# This handler looks if a repository in GitHub has a new tag event(CreateEvent).
## 1) open the tmp data file
# open the repo-events json. The env variable contain the file full-path



criver_file = os.environ['CRIVER_EVENTS_REPO']
with open(criver_file) as json_file:  
    data = json.load(json_file)
    print(data['type'])
