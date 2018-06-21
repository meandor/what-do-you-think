(ns what-do-you-think.go-client-test
  (:require [clojure.test :refer :all]
            [what-do-you-think.go-client :as gc]
            [org.httpkit.client :as http-client]))

(def green-pipeline
  "{
   \"pipelines\": [
                 {
                  \"label\": \"42\",
                         \"name\": \"foobar\",
                  \"natural_order\": 160.5,
                  \"can_run\": true,
                  \"stages\": [
                             {
                              \"result\": \"Passed\"
                              },
                             {
                              \"result\": \"Passed\"
                              },
                             {
                              \"result\": null
                              }
                             ]
                  }
                 ]
   }")

(deftest get-pipeline-status-test
  (testing "should get the status of the pipeline as map"
    (with-redefs [http-client/request (fn [params]
                                        (is (= {:url    "foo-basepipelines/foo-pipeline/history"
                                                :method :get}
                                               params))
                                        (atom {:status 200 :body green-pipeline}))]
      (is (= [{:can_run       true
               :label         "42"
               :name          "foobar"
               :natural_order 160.5
               :stages        [{:result "Passed"}
                               {:result "Passed"}
                               {:result nil}]}]
             (gc/pipeline-history "foo-base" "foo-pipeline"))))))
