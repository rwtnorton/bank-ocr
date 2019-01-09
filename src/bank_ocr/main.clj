(ns bank-ocr.main
  (:require [bank-ocr.scanner :as scanner]
            [bank-ocr.analyzer :as analyzer]
            [bank-ocr.formatter :as formatter]))

(def line-group->formatted-str
  (comp formatter/format-analysis
        analyzer/analyze-ocr-digits
        scanner/line-group->ocr-digits))

(defn rdr->formatted-strs
  [rdr]
  (for [lg (scanner/rdr->line-groups rdr)]
    (line-group->formatted-str lg)))

(defn -main
  [& args]
  (println "Reading OCR account numbers from stdin")
  (doseq [s (rdr->formatted-strs *in*)]
    (println s)))
