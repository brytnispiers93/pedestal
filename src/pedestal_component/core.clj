(ns pedestal-component
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http :as http]))

(defrecord PedestalComponent [service-map]
  component/Lifecycle
  
  (start [this]
         (println "Started pedestal component!"))
  
  (stop [this]
        (println "Stopped pedestal component!")))

(defn new-pedestal-component []
  (map->PedestalComponent {}))