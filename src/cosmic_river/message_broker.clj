(ns cosmic-river.message_broker
  (:require [cosmic-river.rabbitmq :as rabbitmq]
            [cosmic-river.server_config :as criver])
)

;; this namespace will be kind of interface for wrapping all the other different message-broker implementations.
;; rabbitmq, and others. (kafka etc)

;; publish an event.
(defn publish-event [event criver-config]
  (let [mb (get-in (criver/get-criver-config) [:message-broker])]
    (when = (:type mb) "rabbitmq")
     (println "publishing to rabbitmq-event")
     (rabbitmq/publish-event (get-in mb [:config, :exchange-name]) event)
))

(defn init []
  (let [mb (get-in (criver/get-criver-config) [:message-broker])]
    (when = (:type mb) "rabbitmq")
     ;; TODO: it might be that we need more exchange-names, e.g by different types of events
     (rabbitmq/init (get-in mb [:config, :exchange-name])))) 
     ;; kafka ..)) 
