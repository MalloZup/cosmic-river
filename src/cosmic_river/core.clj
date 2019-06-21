(ns cosmic-river.core
  (:require [tentacles.core]
            [tentacles.events]
            [tentacles.repos])
  (:gen-class))


(defn github-events []
  (clojure.edn/read-string (slurp "github-events.edn")))

(defn get-all-events []
  (get-in (github-events) [:github-events]))

(defn get-repo-public-events [user repo]
  (tentacles.events/repo-events)
)
