(defproject org.clojars.brytnispiers/pedestal "0.1.3"
  :description "Wraps a Pedestal server as a component"
  :url "https://github.com/brytnispiers93/pedestal.git"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure          "1.10.0"]
                 [io.pedestal/pedestal.service "0.5.7"]
                 [io.pedestal/pedestal.route   "0.5.7"]
                 [io.pedestal/pedestal.jetty   "0.5.7"]
                 [com.stuartsierra/component   "0.4.0"]]
  :repl-options {:init-ns pedestal})
