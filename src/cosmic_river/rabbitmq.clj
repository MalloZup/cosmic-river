(ns cosmic-river.rabbitmq
  (:require [langohr.core      :as rmq]
            [langohr.channel   :as lch]
            [langohr.exchange  :as le]
            [langohr.basic     :as lb]))


(defn start [ex msg]
  (let [conn  (rmq/connect)
        ch    (lch/open conn)]

    ;; TODO: make this parametrizable in the edn as user input.
    (le/declare ch ex "fanout" {:durable false :auto-delete true})

    ;; start consumer before publish
    (lb/publish ch ex "" msg {:content-type "text/plain" :type "github.repo"})
 
    ;; consider the case that daemon is down and we couldn't close connection
    (rmq/close ch)
    (rmq/close conn)))

