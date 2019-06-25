(ns cosmic-river.rabbitmq
  (:require [langohr.core      :as rmq]
            [langohr.channel   :as lch]
            [langohr.exchange  :as le]
            [langohr.basic     :as lb]))


(defn start []
  (let [conn  (rmq/connect)
        ch    (lch/open conn)
        ;; exchange name: Exchanges receive mesg that are sent by producers
        ;; TODO make that configurable later on
        ex    "criver.github.repository"]

    ;; TODO: make this parametrizable in the edn as user input.
    (le/declare ch ex "fanout" {:durable true :auto-delete false})

    ;; start consumer before publish
    (lb/publish ch ex "" "hello world 01" {:content-type "text/plain" :type "scores.update"})
    (lb/publish ch ex "" "hello earth 02"  {:content-type "text/plain" :type "scores.update"})
    ;; consider the case that daemon is down and we couldn't close connection
    (rmq/close ch)
    (rmq/close conn)))


