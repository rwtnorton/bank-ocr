(ns bank-ocr.scanner-test
  (:require [clojure.test :refer :all]
            [bank-ocr.scanner :as scanner]))

(deftest string->line-groups
  (are [s expected] (= (scanner/string->line-groups s) expected)
    nil []
    "" []
    "foo" [["foo"]]
    "foo\nbar\n\nbaz\nquux\n    \nhmm" [["foo" "bar"]
                                        ["baz" "quux"]
                                        ["hmm"]]))
