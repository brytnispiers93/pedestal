(ns org.clojars.brytnispiers.pedestal
  (:require [com.stuartsierra.component :as component]
            [org.clojars.brytnispiers.errors :as errors]
            [io.pedestal.http :as http]))

(defrecord Pedestal [service-map server]
  component/Lifecycle
  
  (start [this]
         (if server
           this
           (-> service-map
               http/create-server
               http/start
               (partial assoc this :server))))
  
  (stop [this]
        (when server (http/stop server))
        (assoc this :server nil)))

(def required-keys [::http/routes ::http/type ::http/port])

(defn valid-input? [args]
  (cond
    (empty? args) errors/err-empty-input
    (not-every? args required-keys) errors/err-missing-required-field
    :else nil))

(defn new-pedestal [args]
  (let [error (valid-input? args)]
    (if (some? error)
      (throw (Exception. (str "Invalid argument for pedestal component: " error)))
      (map->Pedestal {:service-map (assoc args ::http/join? false)}))))