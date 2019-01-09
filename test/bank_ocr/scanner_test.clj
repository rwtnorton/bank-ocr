(ns bank-ocr.scanner-test
  (:require [clojure.test :refer :all]
            [bank-ocr.scanner :as scanner]
            [clojure.string :as str]))

(declare ocr-digit-for)

(defn- jn [vs] (str/join \newline vs))

(deftest normalize-chars
  (are [samesies] (= (scanner/normalize-chars samesies) samesies)
    nil
    ""
    "|_ "
    "  | _| _||_||_ |_   ||_||_|"
    (str/join ["    _  _     _  _  _  _  _ "
               "  | _| _||_||_ |_   ||_||_|"
               "  ||_  _|  | _||_|  ||_| _|"])
    (jn
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

    (jn
              [" /  _  _     _  _  _  _  _ "
               "  | _| _||_||_ |_   ||-||-|"
               "  ||_  _|  | _||_6  ||_| _|"])
    (jn
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


(deftest line-group->ocr-digits
  (are [lg expected] (= (scanner/line-group->ocr-digits lg) expected)
    [] []
    ;; Each digit string must be three chars wide.
    ["|" "|" "|"] []
    ["  |"
     "  |"
     "  |"] [(jn ["  |" "  |" "  |"])]
    [" _ "
     "| |"
     "|_|"] [(ocr-digit-for 0)]

    [" _    " "| |  |" "|_|  |"]
    [(ocr-digit-for 0) (ocr-digit-for 1)]

    ["    _  _     _  _  _  _  _ "
     "  | _| _||_||_ |_   ||_||_|"
     "  ||_  _|  | _||_|  ||_| _|"]
    (mapv ocr-digit-for (range 1 10))))


(def ocr-digit-for [(jn [" _ "
                         "| |"
                         "|_|"])
                    (jn ["   "
                         "  |"
                         "  |"])
                    (jn [" _ "
                         " _|"
                         "|_ "])
                    (jn [" _ "
                         " _|"
                         " _|"])
                    (jn ["   "
                         "|_|"
                         "  |"])
                    (jn [" _ "
                         "|_ "
                         " _|"])
                    (jn [" _ "
                         "|_ "
                         "|_|"])
                    (jn [" _ "
                         "  |"
                         "  |"])
                    (jn [" _ "
                         "|_|"
                         "|_|"])
                    (jn [" _ "
                         "|_|"
                         " _|"])])
