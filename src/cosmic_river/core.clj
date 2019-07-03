(ns cosmic-river.core
  (:require 
   [tentacles.events]
   [tentacles.core]
   [cosmic-river.message_broker :as msg-broker]
   [cosmic-river.server_config :as criver]
   [cheshire.core :refer :all]
   [clojure.string :as str])
           (:gen-class))


;; TODO: remember that (from: https://developer.github.com/v3/activity/events/#list-issue-events-for-a-rep (:require [clojure.data.json :as json])ository)
;; Only events created within the past 90 days will be included in timelines. Events older than 90 days will not be included (even if the total number of events in the timeline is less than 300).

;; this will contain all different repository/events (uid) e-tag.
;; we need this so we can fetch only events when e-tag change
(def etag-cache (atom {}))

(defn get-repo-events [full-repo-name event exchange-name]
  "read from GitHub api v3 the repo events"
  (let [github-user (first (str/split full-repo-name #"/"))
        github-repo (last (str/split full-repo-name #"/"))
        gh-token (criver/get-gh-token)
        ;; uid used for storing the e-tag to atom
        uid-etag  (keyword (str github-user "-" github-repo "-" event))
        ;; if this hash changes, something on the repo is new/changed and we will send the new raw events.
        etag-hash (:etag (tentacles.core/api-meta (tentacles.events/repo-events github-user github-repo {:oauth-token gh-token})))]
    (when (nil? etag-hash) (println "[DEBUG]: github rate-limit exceed"))
    (when (= etag-hash (get-in @etag-cache [uid-etag])) 
      (println "[DEBUG]:  etag-repository already present in cache")
      (println "[DEBUG]: no new repository event found"))
    ;; etag-hash is not present in atom-cache, we need to get events first then update atom with new et
    (when (not= etag-hash (get-in @etag-cache [uid-etag]))
      (msg-broker/publish-events {:type (get-in (criver/get-criver-config) [:message-broker :type]), :events (tentacles.events/repo-events github-user github-repo), :ex-name  exchange-name})
      (swap! etag-cache merge { uid-etag 
                               (:etag (tentacles.core/api-meta (tentacles.events/repo-events github-user github-repo {:oauth-token gh-token})))}))))  

;; we will need other functions for the other events.
(defn dispatch-all-repo-events []
  "dispatch only repository events"
  (doseq [repo-entry (criver/get-config-repo-events)] 
    (doseq [event (:events repo-entry)]  
      (when (= "repository" (str/lower-case event)) 
        ;; do things with only repo events
        (get-repo-events (:full-repo-name repo-entry) event (:exchange-name repo-entry)))  
      (when (= "issue" (str/lower-case event))
        ;; do things with issue events of repository
        (println "do issue stuff")))))


(defn help-msg-cli []
  (println "use \"init\" for initializing the msg broker")
  (println "use \"start\" daemon after init")
)

(defn start-daemon []
  (while true
    (let [interval (* 5 60 1000)]
      (dispatch-all-repo-events) 
      (Thread/sleep interval))
      (println "[DEBUG:] sleeping for timeout 5 min")))


(defn -main [& args]
  (if-not (empty? args)
    (doseq [arg args]
      (when (= arg "init")
        (msg-broker/init) 
        (System/exit 0) 
        )
      (when (= arg "start")
        (start-daemon))))
  (when (empty? args) 
    (help-msg-cli) 
  )
)

