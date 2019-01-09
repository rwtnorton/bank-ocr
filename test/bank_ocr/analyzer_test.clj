(ns bank-ocr.analyzer-test
  (:require [clojure.test :refer :all]
            [bank-ocr.analyzer :as analyzer]
            [bank-ocr.parser :as parser]
            [bank-ocr.ocr-digit :as ocr-digit :refer [zero
                                                      one
                                                      two
                                                      three
                                                      four
                                                      five
                                                      six
                                                      seven
                                                      eight
                                                      nine]]))

(deftest analyze-ocr-digits
  (testing "invalid input"
    (are [vs] (= ::analyzer/invalid-input
                 (:error (analyzer/analyze-ocr-digits vs)))
      []
      nil
      [1 2 3 4 5 6 7 8]
      [1 2 3 4 5 6 7 8 9 10]))
  (testing "illegal char"
    (let [{:keys [error parsed]}
          (analyzer/analyze-ocr-digits [one two three four five six seven eight
                                        "___\n   \n|_|"])]
      (is (= error ::analyzer/illegal-char))
      (is (= parsed [1 2 3 4 5 6 7 8 ::parser/???]))))
  (testing "bad checksum"
    (let [{:keys [error parsed checksum]}
          (analyzer/analyze-ocr-digits [six six four three seven
                                        one four nine five])]
      (is (= error ::analyzer/bad-checksum))
      (is (= parsed [6 6 4 3 7 1 4 9 5]))
      (is (not (zero? checksum)))))
  (testing "happy path"
    (let [{:keys [error parsed ok]}
          (analyzer/analyze-ocr-digits [three four five eight eight
                                        two eight six five])]
      (is (nil? error))
      (is (= parsed [3 4 5 8 8 2 8 6 5]))
      (is (true? ok)))))
