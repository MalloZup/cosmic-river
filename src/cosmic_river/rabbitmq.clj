(ns cosmic-river.rabbitmq
  (:require [langohr.core      :as rmq]
            [langohr.channel   :as lch]
            [langohr.exchange  :as le]
            [cheshire.core :refer :all]
            [langohr.basic     :as lb]))

;; TODO: investigate if conn/connect can be called without any problems

;; http://clojurerabbitmq.info/articles/excqhanges.html#publishing-messages
(defn publish-event [ex event]
  (let [conn  (rmq/connect)
        ch    (lch/open conn)]
    ;; start consumer before publish
    (lb/publish ch ex "" (generate-smile event) {:content-type "application/json" :type "github.repo"})
    (rmq/close ch)
    (rmq/close conn)))

(defn init [ex]
  (let [conn  (rmq/connect)
        ch    (lch/open conn)]
    (le/declare ch ex "fanout" {:durable false :auto-delete true})))
