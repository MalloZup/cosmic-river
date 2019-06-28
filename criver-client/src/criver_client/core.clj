(ns criver-client.core
  (:require [langohr.queue  :as lq]
            [langohr.core   :as rmq]
            [langohr.channel :as lch]
            [cheshire.core :refer :all]
            [langohr.consumers :as lc]))

(use '[clojure.java.shell :only [sh]])
(use 'clojure.java.io)

(defn client-edn-config []
  (clojure.edn/read-string (slurp "cr-rabbitmq.edn")))

(defn rabbitmq-consumers [] (get-in (client-edn-config) [:rabbitmq-consumers]))

(defn ^:private apply-filters [key-f event]
(doall (reduce #((keyword %) event)  key-f )))

(defn gh-filter-events [events-json key-filter]
  "return only events filtered by user"
   (doall (map #(apply-filters key-filter %)  events-json )))
  
(defn start-consumers  []
  "Starts a consumer bound to the given topic exchange in a separate thread"
  (doseq [consumer (rabbitmq-consumers)]
   (let [qe-name (format "criver.%s" (:qe-name consumer))
        conn  (rmq/connect)
        ex-name (:exchange-name consumer)
        ch    (lch/open conn)
        shell-command (:shell-command consumer)
        key-filter (:key-filter consumer)
        msg-handler  (fn [ch {:keys [content-type delivery-tag type] :as meta} ^bytes payload]
                       (println (gh-filter-events (parse-string payload true) key-filter))
                       (println (:out (sh shell-command))))]

     
    (println (str "starting consumer with queue-name: " qe-name "and ex-name: " ex-name))
    (lq/declare ch qe-name {:exclusive false :auto-delete true})
    (lq/bind    ch qe-name ex-name)
    (lc/subscribe ch qe-name msg-handler {:auto-ack true}))))
