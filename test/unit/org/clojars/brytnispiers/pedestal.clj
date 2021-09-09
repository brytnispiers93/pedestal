(ns unit.org.clojars.brytnispiers.pedestal
  (:require [clojure.test :refer :all]
            [com.stuartsierra.component :as component]
            [org.clojars.brytnispiers.pedestal :as pedestal]
            [org.clojars.brytnispiers.errors :as errors]
            [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]))

(defn create-valid-pedestal []
  (pedestal/new-pedestal {::http/type :jetty
                          ::http/routes (route/expand-routes #{["/test" :get #() :route-name :test]})
                          ::http/port 9337}))

(deftest new-pedestal
  (testing "nil input"
    (is (thrown-with-msg? Exception
                 (re-pattern errors/err-empty-input)
                 (pedestal/new-pedestal nil))))
  
  (testing "empty input"
    (is (thrown-with-msg? Exception
                          (re-pattern errors/err-empty-input)
                          (pedestal/new-pedestal {}))))
  
  (testing "missing routes"
    (is (thrown-with-msg? Exception
                          (re-pattern errors/err-missing-required-field)
                          (pedestal/new-pedestal {::http/type :jetty
                                                  ::http/port 9337}))))
  
  (testing "missing type"
    (is (thrown-with-msg? Exception
                          (re-pattern errors/err-missing-required-field)
                          (pedestal/new-pedestal {::http/routes #{["/test" :get #{} :route-name :test]}
                                                  ::http/port 9337}))))
  
  (testing "missing port"
    (is (thrown-with-msg? Exception
                          (re-pattern errors/err-missing-required-field)
                          (pedestal/new-pedestal {::http/type :jetty
                                                  ::http/routes #{["/test" :get #{} :route-name :test]}}))))

  (testing "valid input"
    (is (create-valid-pedestal))))

(deftest lifecycle
  (testing "pedestal component lifecycle"
    (let [pedestal (create-valid-pedestal)]
      (is (-> pedestal
          component/stop  ; stopping non-started component
          component/start ; starting component
          component/start ; testing idempotence
          component/start ; testing idempotence
          component/stop
          component/stop
          component/start
          component/stop)))))