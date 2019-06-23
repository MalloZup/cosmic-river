(ns cosmic-river.core
  (:require 
            [tentacles.events])
  (:gen-class))


(defn github-events []
  (clojure.edn/read-string (slurp "github-events.edn")))

(defn get-all-events []
  (get-in (github-events) [:github-events]))

;; TODO: use conditional request to don't get always same events
(defn get-repo-events [user repo]
  (tentacles.events/repo-events)
)

(defn dispatch-events [github-events]
;; based on the type of event, call the right function
;; e.g if repo call the repo function
  (->> (first github-events)
        (println ))
)
