(ns what-do-you-think.core
  (:require [de.otto.tesla.system :as system]
            [com.stuartsierra.component :as c]
            [de.otto.goo.goo :as goo]
            [clojure.tools.logging :as log]
            [iapetos.collector.jvm :as jvm]
            [de.otto.tesla.serving-with-httpkit :as httpkit]
            [what-do-you-think.endpoint :as ep])
  (:gen-class))

(defn what-do-you-think-system [runtime-config]
  (-> (system/base-system runtime-config)
      (assoc :endpoint (c/using (ep/new-endpoint) [:config :handler]))
      (httpkit/add-server :endpoint)))

(defonce _ (jvm/initialize (goo/snapshot)))
(defonce _ (Thread/setDefaultUncaughtExceptionHandler
             (reify Thread$UncaughtExceptionHandler
               (uncaughtException [_ thread ex]
                 (log/error ex "Uncaught exception on " (.getName thread))))))

(defn -main [& _]
  (system/start (what-do-you-think-system {})))
