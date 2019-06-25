(ns criver-client.core
  (:require [langohr.queue  :as lq]
            [langohr.core   :as rmq]
            [langohr.channel :as lch]
            [langohr.consumers :as lc]))


(defn msg-handler
  [ch {:keys [content-type delivery-tag type] :as meta} ^bytes payload]
  (println (format "[consumer] Received a message: %s, delivery tag: %d, content type: %s, type: %s"  (String. payload "UTF-8") delivery-tag content-type type)))

(defn start-consumer  [topic-name name]
  "Starts a consumer bound to the given topic exchange in a separate thread"
  ;; qe = queue
  (let [qe-name (format "criver.%s" name)
        conn  (rmq/connect)
        ch    (lch/open conn)]

    (lq/declare ch qe-name {:exclusive false :auto-delete true})
    (lq/bind    ch qe-name topic-name)
    (lc/subscribe ch qe-name msg-handler {:auto-ack true})))
