(ns cosmic-river.core
  (:require 
   [tentacles.events]
   [tentacles.core]
   [cosmic-river.rabbitmq :as rabbitmq]
   [clojure.string :as str])
           (:gen-class))


;; TODO: remember that (from: https://developer.github.com/v3/activity/events/#list-issue-events-for-a-repository)
;; Only events created within the past 90 days will be included in timelines. Events older than 90 days will not be included (even if the total number of events in the timeline is less than 300).

;; this will contain all different repository/events (uid) e-tag.
;; we need this so we can fetch only events when e-tag change
(def etag-cache (atom {}))

(defn criver-config []
  (clojure.edn/read-string (slurp "criver-config.edn")))

(defn get-all-events []
  (get-in (criver-config) [:github-events]))

(defn get-config-repo-events []
  "read config and get only repository events"
  (get-in  (get-all-events) [:repository-events]))

;; TODO: in case 90 days have passed, the repo/events return a nil.
;; Research if this is problematic or not
(defn get-repo-events [full-repo-name event]
  "read from GitHub api v3 the repo events"
  (let [github-user (first (str/split full-repo-name #"/"))
        github-repo (last (str/split full-repo-name #"/"))
        ;; uid used for storing the e-tag to atom
        uid-etag  (keyword (str github-user "-" github-repo "-" event))
        etag-hash (:etag (tentacles.core/api-meta (tentacles.events/repo-events github-user github-repo)))]
    ;;etag-hash is not present in atom-cache, we need to get events first then update atom with new et
    (when (not= etag-hash (get-in @etag-cache [uid-etag]))
      ;; TODO: this will use messagebroker to send this events
      (println (tentacles.events/repo-events github-user github-repo)) 
      (swap! etag-cache merge { uid-etag 
                              (:etag (tentacles.core/api-meta (tentacles.events/repo-events github-user github-repo)))}))))  


(defn dispatch-all-repo-events []
  "dispatch only repository events"
  (doseq [repo-entry (get-config-repo-events)] 
    (doseq [event (:events repo-entry)]  
      (when (= "repository" (str/lower-case event)) 
        ;; do things with only repo events
        (get-repo-events (:full-repo-name repo-entry) event))  
      (when (= "issue" (str/lower-case event))
        ;; do things with issue events of repository
        (println "do issue stuff")))))
  

(defn -main []
   ;; it should be easy to add other dispatcher which are executed in parallel.
   ;; (dispatch-all-repo-events)
   ;; TODO: THIS IS here only for convenience
   (rabbitmq/start)
)

(defn daemonize []
  (while true
      (let [interval (* 5 60 1000)] 
      (Thread/sleep interval))))
