(ns cosmic-river.message_broker
  (:require [cosmic-river.rabbitmq :as rabbitmq]
            [cosmic-river.server_config :as criver])
)

;; this namespace will be kind of interface for wrapping all the other different message-broker implementations.
;; rabbitmq, and others. (kafka etc)


(defn publish-event [event criver-config exchange-name]
  (let [mb (get-in (criver/get-criver-config) [:message-broker])]
    (when = (:type mb) "rabbitmq")
       (println "publishing event using rabbitmq")
       (rabbitmq/publish-event exchange-name event)
))

(defn init []
  (let [mb (get-in (criver/get-criver-config) [:message-broker])]
    (when = (:type mb) "rabbitmq")
        (doseq [exchange-name (get-in (criver/get-criver-config) [:message-broker, :exchange-names] )]
      (rabbitmq/init exchange-name)))) 
     ;; kafka ..)) 
