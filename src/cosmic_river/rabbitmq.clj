(ns cosmic-river.rabbitmq
  (:require [langohr.core      :as rmq]
            [langohr.channel   :as lch]
            [langohr.exchange  :as le]
            [langohr.basic     :as lb]))

;; TODO: investigate if conn/connect can be called without any problems


(defn publish [ex msg]
  (let [conn  (rmq/connect)
        ch    (lch/open conn)]
    ;; start consumer before publish
    (lb/publish ch ex "" msg {:content-type "text/plain" :type "github.repo"})
    (rmq/close ch)
    (rmq/close conn)))

(defn init [ex]
  (let [conn  (rmq/connect)
        ch    (lch/open conn)]
    (le/declare ch ex "fanout" {:durable false :auto-delete true})))
