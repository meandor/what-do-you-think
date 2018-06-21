(ns what-do-you-think.endpoint
  (:require [clojure.tools.logging :as log]
            [de.otto.tesla.stateful.handler :as handler]
            [com.stuartsierra.component :as c]
            [compojure.core :as cc]
            [compojure.route :as route]
            [hiccup.page :as page]
            [what-do-you-think.go-client :as gc]))

(defn pipeline-ok? [base-api pipeline-name]
  (let [latest-build (first (gc/pipeline-history base-api pipeline-name))]
    (reduce (fn [acc stage] (and acc (not= "Failed" (:result stage)))) true (:stages latest-build))))

(defn response [{:keys [config]} _]
  (page/html5
    [:head
     [:title "Dragon Killers"]
     (page/include-css "style.css")
     [:meta {:http-equiv "refresh" :content "3; URL=http://localhost:8080/"}]]

    [:body
     (if (pipeline-ok? (get-in config [:config :pipeline-base-api]) (get-in config [:config :pipeline-name]))
       [:div {:class "ok"} ":D"]
       [:div {:class "error"} ":'("])]))

(defn endpoint-filter [handler]
  (cc/routes
    (cc/GET "/" req (handler req))
    (route/resources "/")))

(defn create-routes [self]
  (->> (partial response self)
       (endpoint-filter)))

(defrecord Endpoint [handler]
  c/Lifecycle
  (start [self]
    (log/info "-> starting Endpoint")
    (handler/register-handler handler (create-routes self))
    self)
  (stop [_]
    (log/info "<- stopping Endpoint")))

(defn new-endpoint []
  (map->Endpoint {}))
