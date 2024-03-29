(defproject criver-client "0.1.0"
  :description "cosmic-river clojure client. "
  :url "https://github.com/MalloZup/cosmic-river"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [cheshire "5.8.1"]
                 [com.novemberain/langohr "5.1.0"]]
  :main criver-client.core
  :repl-options {:init-ns criver-client.core})
