(ns bank-ocr.parser-test
  (:require [clojure.test :refer :all]
            [bank-ocr.parser :as parser]
            [bank-ocr.ocr-digit :as ocr-digit]))

(deftest parse-ocr-digit
  (are [v expected] (= (parser/parse-ocr-digit v) expected)
    nil ::parser/???
    [] ::parser/???
    42 ::parser/???
    "one" ::parser/???
    ocr-digit/zero 0
    ocr-digit/one 1
    ocr-digit/two 2
    ocr-digit/three 3
    ocr-digit/four 4
    ocr-digit/five 5
    ocr-digit/six 6
    ocr-digit/seven 7
    ocr-digit/eight 8
    ocr-digit/nine 9))
