(defproject test_api_clojure_crud "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [ring "1.9.5"]
                 [cheshire "5.10.0"]
                 [ring/ring-json "0.5.0"]]
  :main ^:skip-aot test_api_clojure_crud.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
