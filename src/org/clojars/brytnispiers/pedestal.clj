(ns org.clojars.brytnispiers.pedestal
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http :as http]))

(defrecord Pedestal [service-map]
  component/Lifecycle
  
  (start [this]
         (println "Started pedestal component!")
         this)
  
  (stop [this]
        (println "Stopped pedestal component!")
        this))

(defn new-pedestal []
  (map->Pedestal {}))