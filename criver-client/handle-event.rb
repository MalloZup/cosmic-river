#! /usr/bin/ruby

### ------------------------------------
####  what does this event-handler it does?: ####

# This handler looks if a repository in GitHub has a new tag event(CreateEvent).
#
# A new tag,  might correspond to a release event (e.g we have a new release of kubernetes/clojure/ etc),
# if there is a new tag/release, perform some action, e.g send for example a mesg to a slack/rocketchat, or other any actions.
### ------------------------------------
require 'json'

# 0) scheduling the event handler
#   Remember to schedule this handler from  cr-rabbitmq.edn
#              :rabbitmq-consumers [
#              with
#               {:qe-name "my-que-name" :exchange-name "my-exchange-name" :shell-command "FULL_PATH_TO_HANDLER/handle-event.rb" },

# important is only the handle-event full path.


## 1) open the tmp data file
# open the repo-events json. The env variable contain the file full-path
#file = File.open(ENV['CRIVER_EVENTS_REPO'])
file = File.open("/tmp/criver-neoex-53-criver.na-events.json")
criver_repo_data = JSON.load file

# 3) perform action with data filtered..
def perform_action(event_data)
  puts event_data
  puts event_data[:repo_name]
  puts "--------------"
end


## 2) Filter the events we are interested

# for a list of events type https://developer.github.com/v3/activity/events/types/
# select the event we are interested, in this case:
# CreateEvent: Represents a created branch or tag.
criver_repo_data.each do |event| 
	# filter events
	if event["type"] == "CreateEvent"
          # we are interested only on master tag/release
	  if event["payload"]["master_branch"] == "master"
            ## we want only a specific repository name
            if event["repo"]["name"] == "MalloZup/fullrocketmetal"
              perform_action({repo_name: event["repo"]["name"],
	                    repo_url: event["repo"]["url"],
			    tag_number: event["payload"]["ref"],
			    ref_type: event["payload"]["ref_type"],
	                    created_at: event["created_at"]})
           end
          end
        end
end
