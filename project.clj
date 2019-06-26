(defproject cosmic-river "0.1.0-SNAPSHOT"
  :description "Cosmic-river: GitHub event middleware. Composable for application and tool developers"
  :url "https://github.com/MalloZup/cosmic-river"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                [com.novemberain/langohr "5.1.0"]
                [cheshire "5.8.1"]
                [irresponsible/tentacles "0.6.3"]]
  :main cosmic-river.core
  :repl-options {:init-ns cosmic-river.core}
)
