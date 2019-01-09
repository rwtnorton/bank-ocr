(ns bank-ocr.formatter-test
  (:require [clojure.test :refer :all]
            [bank-ocr.formatter :as formatter]
            [bank-ocr.analyzer :as analyzer]
            [bank-ocr.parser :as parser]))

(deftest format-analysis
  (testing "should never happen"
    (is (thrown? IllegalStateException (formatter/format-analysis {}))))
  (testing "invalid input"
    (is (= "*** INVALID INPUT ***"
           (formatter/format-analysis {:error ::analyzer/invalid-input}))))
  (testing "illegal char"
    (is (= "????????? ILL"
           (formatter/format-analysis {:error  ::analyzer/illegal-char
                                       :parsed (repeat 9 ::parser/???)})))
    (is (= "12345678? ILL"
           (formatter/format-analysis {:error  ::analyzer/illegal-char
                                       :parsed [1 2 3 4 5 6 7 8
                                                ::parser/???]}))))
  (testing "bad checksum"
    (is (= "664371495 ERR"
           (formatter/format-analysis {:error  ::analyzer/bad-checksum
                                       :parsed [6 6 4 3 7 1 4 9 5]}))))
  (testing "happy paths"
    (is (= "345882865"
           (formatter/format-analysis {:ok     true
                                       :parsed [3 4 5 8 8 2 8 6 5]})))
    (is (= "457508000"
           (formatter/format-analysis {:ok     true
                                       :parsed [4 5 7 5 0 8 0 0 0]})))))
