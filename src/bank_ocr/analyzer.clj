(ns bank-ocr.analyzer
  (:require [bank-ocr.parser :as parser :refer [parse-ocr-digit]]
            [bank-ocr.checksum :as checksum]))

(defn analyze-ocr-digits
  [ocr-digits]
  (if (or (not (seq ocr-digits))
          (not= 9 (count ocr-digits)))
    {:orig  ocr-digits
     :error ::invalid-input}
    (let [vs (map parse-ocr-digit ocr-digits)]
      (if (not-every? int? vs)
        {:orig   ocr-digits
         :parsed vs
         :error  ::illegal-char}
        (let [sum (checksum/calculate vs)]
          (if-not (zero? sum)
            {:orig     ocr-digits
             :parsed   vs
             :error    ::bad-checksum
             :checksum sum}
            {:orig   ocr-digits
             :parsed vs
             :ok     true}))))))
