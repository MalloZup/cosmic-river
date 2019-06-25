(ns cosmic-river.rabbitmq
  (:gen-class)
  (:require [langohr.core      :as rmq]
            [langohr.channel   :as lch]
            [langohr.queue     :as lq]
            [langohr.exchange  :as le]
            [langohr.consumers :as lc]
            [langohr.basic     :as lb]))


(defn start-consumer
  "Starts a consumer bound to the given topic exchange in a separate thread"
  [ch topic-name username]
  (let [queue-name (format "nba.newsfeeds.%s" username)
        handler    (fn [ch {:keys [content-type delivery-tag type] :as meta} ^bytes payload]
                     (println (format "[consumer] %s received %s" username (String. payload "UTF-8"))))]
    (lq/declare ch queue-name {:exclusive false :auto-delete true})

    (lc/subscribe ch queue-name handler {:auto-ack true})))

(defn -main
  [& args]
  (let [conn  (rmq/connect)
        ch    (lch/open conn)
        ;; exchange name: Exchanges receive mesg that are sent by producers
        ex    "nba.scores"]

    ;; TODO: make this parametrizable in the edn as user input.
    (le/declare ch ex "fanout" {:durable true :auto-delete false})
    ;; START a consumer before publish
    (lb/publish ch ex "" "BOS 101, NYK 89" {:content-type "text/plain" :type "scores.update"})
    (lb/publish ch ex "" "ORL 85, ATL 88"  {:content-type "text/plain" :type "scores.update"})
    (Thread/sleep 2000)
    ;; consider the case that daemon is down and we couldn't close connection
    (rmq/close ch)
    (rmq/close conn)))


