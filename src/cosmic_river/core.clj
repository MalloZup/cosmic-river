(ns cosmic-river.core
  (:require 
   [tentacles.events]
   [tentacles.core]
   [cosmic-river.message_broker :as msg-broker]
   [cosmic-river.server_config :as criver]
   [cheshire.core :refer :all]
   [clojure.string :as str])
           (:gen-class))


;; get only last 90 days events.
(defn get-repo-events [full-repo-name event exchange-name]
  "read from GitHub api v3 the repo events"
  (let [github-user (first (str/split full-repo-name #"/"))
        github-repo (last (str/split full-repo-name #"/"))
        gh-token (criver/get-gh-token)]
      (msg-broker/publish-events {:type (get-in (criver/get-criver-config) [:message-broker :type]), :events (tentacles.events/repo-events github-user github-repo), :ex-name  exchange-name})))

;; we will need other functions for the other events.
;; TODO implement multi-methods later
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

