(defproject what-do-you-think "0.1.1-SNAPSHOT"
  :description "Go CD Pipeline monitor"
  :url "https://github.com/meandor/what-do-you-think"
  :license {:name "MIT License"
            :url  "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [de.otto/tesla-microservice "0.12.0"]
                 [de.otto/tesla-httpkit "1.0.1"]
                 [org.clojure/tools.logging "0.4.0"]
                 [ch.qos.logback/logback-classic "1.2.3"]
                 [hiccup "1.0.5"]]
  :main ^:skip-aot what-do-you-think.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
