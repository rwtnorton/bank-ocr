(ns bank-ocr.checksum-test
  (:require [clojure.test :refer :all]
            [bank-ocr.checksum :as checksum]))

(deftest calculate
  (testing "with invalid input"
    (is (thrown? AssertionError (checksum/calculate nil)))
    (is (thrown? AssertionError (checksum/calculate [])))
    (is (thrown? AssertionError (checksum/calculate [1 2 "42"])))
    (is (thrown? AssertionError (checksum/calculate [1 2 3])))
    (is (thrown? AssertionError (checksum/calculate [1 2 3 4 5 6 7 8])))
    (is (thrown? AssertionError (checksum/calculate [1 2 3 4 5 6 7 8 9 0])))
    (is (thrown? AssertionError (checksum/calculate [1 2 3 4 5 6 7 8 10])))
    (is (thrown? AssertionError (checksum/calculate [1 2 3 4 5 6 7 8 -1]))))
  (testing "happy paths"
    (are [digits expected] (checksum/calculate digits)
      [3 4 5 8 8 2 8 6 5] 0
      [4 5 7 5 0 8 0 0 0] 0
      [6 6 4 3 7 1 4 9 5] 2)))
