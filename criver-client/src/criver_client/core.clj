(ns criver-client.core
  (:require [langohr.queue  :as lq]
            [langohr.core   :as rmq]
            [langohr.channel :as lch]
            [cheshire.core :refer :all]
            [langohr.consumers :as lc]))

(use '[clojure.java.shell :only [sh]])
(use 'clojure.java.io)


(defn exec-cmd-and-pass-env-var [shell-command tmp-file-path]
  (println (:out (sh shell-command :env {"CRIVER_EVENTS_FPATH" tmp-file-path}))))

(defn dump-events-to-tmp-file [events-json tmp-file-name]
 (with-open [wrtr (writer tmp-file-name)]
 (.write wrtr events-json)))
 
(defn client-edn-config []
  (clojure.edn/read-string (slurp "cr-rabbitmq.edn")))

(defn rabbitmq-consumers [] (get-in (client-edn-config) [:rabbitmq-consumers]))

(defn start-consumers  []
  "Starts a consumer bound to the given topic exchange in a separate thread"
  (doseq [consumer (rabbitmq-consumers)]
   (let [qe-name (format "criver.%s" (:qe-name consumer))
         conn  (rmq/connect)
        ex-name (:exchange-name consumer)
        ch    (lch/open conn)
        shell-command (:shell-command consumer)
        tmp-file-path (str "/tmp/" "criver-" ex-name "-" (str (rand-int 90)) "-" qe-name "-" "events.json" )
        msg-handler  (fn [ch {:keys [content-type delivery-tag type] :as meta} ^bytes payload]
                       (println payload)
                       (dump-events-to-tmp-file (String. payload "UTF-8") tmp-file-path)
                       (exec-cmd-and-pass-env-var shell-command tmp-file-path)
                       )]

     
    (println (str "[DEBUG]: starting consumer with queue-name: " qe-name "and ex-name: " ex-name))

    (lq/declare ch qe-name {:exclusive false :auto-delete true})
    (lq/bind    ch qe-name ex-name)
    (lc/subscribe ch qe-name msg-handler {:auto-ack true}))))
