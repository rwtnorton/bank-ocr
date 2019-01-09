(ns bank-ocr.parser
  (:require [bank-ocr.ocr-digit :as ocr-digit]))

(defn parse-ocr-digit
  [ocr-digit]
  ;; Note:  Need to use `condp` instead of `case` because ocr-digits
  ;;        are not compile-time constants.
  (condp = ocr-digit
    ocr-digit/zero 0
    ocr-digit/one 1
    ocr-digit/two 2
    ocr-digit/three 3
    ocr-digit/four 4
    ocr-digit/five 5
    ocr-digit/six 6
    ocr-digit/seven 7
    ocr-digit/eight 8
    ocr-digit/nine 9
    ;; else
    ::???))
