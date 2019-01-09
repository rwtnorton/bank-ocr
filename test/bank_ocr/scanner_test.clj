(ns bank-ocr.scanner-test
  (:require [clojure.test :refer :all]
            [bank-ocr.scanner :as scanner]
            [bank-ocr.ocr-digit :as ocr-digit :refer [ocr-digit-for]]
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
  (are [s expected] (= expected (scanner/string->line-groups s))
    nil []
    "" []

    "foo\nbar\nbaz\n\nbaz\nquux\nxyzzy\n\n"
    [["foo" "bar" "baz"]
     ["baz" "quux" "xyzzy"]]))

(deftest line-group->ocr-digits
  (are [lg expected] (= expected (scanner/line-group->ocr-digits lg))
    [] []

    [" _ "
     "| |"
     "|_|"] [ocr-digit/zero ocr-digit/blank ocr-digit/blank
             ocr-digit/blank ocr-digit/blank ocr-digit/blank
             ocr-digit/blank ocr-digit/blank ocr-digit/blank]

    [" _    "
     "| |  |"
     "|_|  |"] [ocr-digit/zero ocr-digit/one ocr-digit/blank
                ocr-digit/blank ocr-digit/blank ocr-digit/blank
                ocr-digit/blank ocr-digit/blank ocr-digit/blank]

    ["    _  _     _  _  _  _  _ "
     "  | _| _||_||_ |_   ||_||_|"
     "  ||_  _|  | _||_|  ||_| _|"]
    (mapv ocr-digit-for (range 1 10))))
