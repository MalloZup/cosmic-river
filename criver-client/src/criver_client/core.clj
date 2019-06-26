(ns criver-client.core
  (:require [langohr.queue  :as lq]
            [langohr.core   :as rmq]
            [langohr.channel :as lch]
            [langohr.consumers :as lc]))

(defn client-edn-config []
  (clojure.edn/read-string (slurp "cr-rabbitmq.edn")))

(def rabbitmq-consumers (get-in (client-edn-config) [:rabbitmq-consumers]))

(defn msg-handler
  [ch {:keys [content-type delivery-tag type] :as meta} ^bytes payload]
  
  (println (format "[consumer] Received a message: %s, delivery tag: %d, content type: %s, type: %s"  (String. payload "UTF-8") delivery-tag content-type type)))

(defn start-consumers  []
  "Starts a consumer bound to the given topic exchange in a separate thread"
  (doseq [consumer rabbitmq-consumers]
   (let [qe-name (format "criver.%s" (:qe-name consumer))
        conn  (rmq/connect)
        ex-name (:exchange-name consumer)
        ch    (lch/open conn)]
     (println (str "starting consumer with queue-name" qe-name))
    (lq/declare ch qe-name {:exclusive false :auto-delete true})
    (lq/bind    ch qe-name ex-name)
    (lc/subscribe ch qe-name msg-handler {:auto-ack true}))))
 
