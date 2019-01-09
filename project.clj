(defproject bank-ocr "0.1.0-SNAPSHOT"
  :description "Scans seven-segment display numbers and converts into their arabic numeral counterparts"
  :url ""
  :license {:name "Apache License, version 2.0"
            :url "https://www.apache.org/licenses/LICENSE-2.0"}
  :dependencies [[org.clojure/clojure "1.9.0"]]
  :repl-options {:init-ns bank-ocr.main}
  :main bank-ocr.main)
