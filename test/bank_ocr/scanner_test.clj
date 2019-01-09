(ns bank-ocr.scanner-test
  (:require [clojure.test :refer :all]
            [bank-ocr.scanner :as scanner]
            [clojure.string :as str]))

(deftest normalize-chars
  (are [samesies] (= (scanner/normalize-chars samesies) samesies)
    nil
    ""
    "|_ "
    "  | _| _||_||_ |_   ||_||_|"
    (str/join ["    _  _     _  _  _  _  _ "
               "  | _| _||_||_ |_   ||_||_|"
               "  ||_  _|  | _||_|  ||_| _|"])
    (str/join \newline
              ["    _  _     _  _  _  _  _ "
               "  | _| _||_||_ |_   ||_||_|"
               "  ||_  _|  | _||_|  ||_| _|"
               ""]))
  (are [s expected] (= (scanner/normalize-chars s) expected)
    "foo" "???"

    (str/join [" /  _  _     _  _  _  _  _ "
               "  | _| _||_||_ |_   ||-||-|"
               "  ||_  _|  | _||_6  ||_| _|"])
    (str/join [" ?  _  _     _  _  _  _  _ "
               "  | _| _||_||_ |_   ||?||?|"
               "  ||_  _|  | _||_?  ||_| _|"])

    (str/join \newline
              [" /  _  _     _  _  _  _  _ "
               "  | _| _||_||_ |_   ||-||-|"
               "  ||_  _|  | _||_6  ||_| _|"])
    (str/join \newline
              [" ?  _  _     _  _  _  _  _ "
               "  | _| _||_||_ |_   ||?||?|"
               "  ||_  _|  | _||_?  ||_| _|"])))

(deftest string->line-groups
  (are [s expected] (= (scanner/string->line-groups s) expected)
    nil []
    "" []
    "foo" [["foo"]]
    "foo\nbar\n\nbaz\nquux\n    \nhmm" [["foo" "bar"]
                                        ["baz" "quux"]
                                        ["hmm"]]))
