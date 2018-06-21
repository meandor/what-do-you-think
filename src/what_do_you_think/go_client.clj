(ns what-do-you-think.go-client
  (:require [org.httpkit.client :as http-client]
            [clojure.data.json :as json]))

(defn- get-pipeline-history [base-url pipeline-name]
  (http-client/request {:url    (str base-url "pipelines/" pipeline-name "/history")
                        :method :get}))

(defn pipeline-history [base-url pipeline-name]
  (let [request (get-pipeline-history base-url pipeline-name)]
    (if (= 200 (:status @request))
      (:pipelines (json/read-str (:body @request) :key-fn keyword))
      :error)))
