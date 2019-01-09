(ns bank-ocr.helper
  (:require [clojure.string :as str]))

(defn ocr-lines
  [line1 line2 line3]
  (str/join \newline [line1 line2 line3 "" ""]))
