(ns cosmic-river.message_broker
  (:require [cosmic-river.rabbitmq :as rabbitmq]
            [cosmic-river.server_config :as criver])
)

;; this namespace will be kind of interface for wrapping all the other different message-broker implementations.
;; rabbitmq, and others. (kafka etc)

;; dispatch on type of message broker
(defmulti publish-events (fn [message-broker] (:type message-broker)))

(defmethod publish-events "rabbitmq" [data] 
       (println "[DEBUG]: publishing event using rabbitmq")
       (rabbitmq/publish-event (:ex-name data) (:events data)))


;; TODO: implement multi-method for this too
(defn init []
  (let [mb (get-in (criver/get-criver-config) [:message-broker])]
    (when = (:type mb) "rabbitmq")
        (println "[DEBUG]: using rabbitmq as message broker")
        (doseq [exchange-name (:exchange-names mb)]
          (println (str "[DEBUG]: initializing exchange: " exchange-name))
          (rabbitmq/init exchange-name)))) 
     ;; kafka ..)) 
