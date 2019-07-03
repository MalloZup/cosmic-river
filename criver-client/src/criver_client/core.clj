(ns criver-client.core
  (:require [langohr.queue  :as lq]
            [langohr.core   :as rmq]
            [langohr.channel :as lch]
            [cheshire.core :refer :all]
            [langohr.consumers :as lc])
   (:gen-class))

(use '[clojure.java.shell :only [sh]])

(def repo-event-env-var "CRIVER_EVENTS_REPO")
(defn exec-cmd-and-pass-env-var [shell-command tmp-file-path env-var-name]
  (println (:out (sh shell-command :env {env-var-name tmp-file-path}))))

(defn dump-events-to-tmp-file [events-json tmp-file-name]
 (generate-stream events-json (clojure.java.io/writer tmp-file-name)))
 
(defn client-edn-config []
  (clojure.edn/read-string (slurp "cr-rabbitmq.edn")))

(defn rabbitmq-consumers [] 
  (get-in (client-edn-config) [:rabbitmq-consumers]))

(defn start-consumers  []
  "Starts a consumer bound to the given topic exchange in a separate thread"
  (doseq [consumer (rabbitmq-consumers)]
   (let [ conn  (rmq/connect)
        ex-name (:exchange-name consumer)
        ch    (lch/open conn)
        qe-name (lq/declare-server-named ch {:exclusive false :auto-delete true})
        shell-command (:shell-command consumer)
        tmp-file-path (str "/tmp/" "criver-" ex-name "-" (str (rand-int 90)) "-" qe-name "-" "events.json" )
        msg-handler  (fn [ch {:keys [content-type delivery-tag type] :as meta} ^bytes payload]
                       (dump-events-to-tmp-file (parse-smile payload) tmp-file-path)
                       (exec-cmd-and-pass-env-var shell-command tmp-file-path repo-event-env-var)
                       )]
     
    (println (str "[DEBUG]: starting consumer with queue-name: " qe-name "and ex-name: " ex-name))

    (lq/bind    ch qe-name ex-name)
    (lc/subscribe ch qe-name msg-handler {:auto-ack true}))))

(defn -main  []
  (start-consumers)
)
