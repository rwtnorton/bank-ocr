(defproject bank-ocr "0.1.0"
  :description "Scans seven-segment display numbers and converts into their arabic numeral counterparts"
  :url "https://github.com/rwtnorton/bank-ocr"
  :license {:name "Apache License, version 2.0"
            :url  "https://www.apache.org/licenses/LICENSE-2.0"}
  :dependencies [[org.clojure/clojure "1.10.0"]]
  :repl-options {:init-ns bank-ocr.main}
  :min-lein-version "2.0.0"
  :uberjar-name "bank-ocr.jar"
  :main ^{:skip-aot true} bank-ocr.main
  :profiles {:uberjar {:aot [bank-ocr.main]}})
