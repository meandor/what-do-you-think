(ns what-do-you-think.go-client
  (:require [org.httpkit.client :as http-client]
            [clojure.data.json :as json]
            [clojure.tools.logging :as log]))

(defn- get-pipeline-history [base-url pipeline-name]
  (log/debug "accessing: " (str base-url "pipelines/" pipeline-name "/history"))
  (http-client/request {:url    (str base-url "pipelines/" pipeline-name "/history")
                        :method :get}))

(defn pipeline-history [base-url pipeline-name]
  (let [request (get-pipeline-history base-url pipeline-name)]
    (if (= 200 (:status @request))
      (:pipelines (json/read-str (:body @request) :key-fn keyword))
      :error)))
