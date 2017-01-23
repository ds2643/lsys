(defproject lsys "0.1.0-SNAPSHOT"
  :description "simple l-system experiments"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [quil "2.5.0"]]
  :main ^:skip-aot lsys.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
