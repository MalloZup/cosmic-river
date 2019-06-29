(ns cosmic-river.server_config)

(defn get-criver-config []
  (-> (clojure.edn/read-string (slurp "criver-config.edn"))
  (get-in ,,, [:criver-config])
))


(defn get-config-repo-events []
  "read config and get only repository events"
  (get-in  (get-criver-config) [:repository-events]))
  
(defn get-gh-token []
  (get-in  (get-criver-config) [:oauth-token]))
