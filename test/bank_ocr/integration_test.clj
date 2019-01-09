(ns bank-ocr.integration-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [bank-ocr.helper :refer [ocr-lines]]
            [bank-ocr.scanner :as scanner]
            [bank-ocr.parser :as parser]
            [bank-ocr.analyzer :as analyzer]
            [bank-ocr.formatter :as formatter]))

(def line-group->formatted-str
  (comp formatter/format-analysis
        analyzer/analyze-ocr-digits
        scanner/line-group->ocr-digits))

(defn ocr-str->formatted-strs
  [s]
  (for [lg (scanner/string->line-groups s)]
    (line-group->formatted-str lg)))

(deftest integrations
  (are [ocr-str expected] (= expected (ocr-str->formatted-strs ocr-str))

    (ocr-lines " _  _  _  _  _  _  _  _  _ "
               "| || || || || || || || || |"
               "|_||_||_||_||_||_||_||_||_|") ["000000000"]

    (ocr-lines ""
               "  |  |  |  |  |  |  |  |  |"
               "  |  |  |  |  |  |  |  |  |") ["111111111 ERR"]

    (ocr-lines " _  _  _  _  _  _  _  _  _ "
               " _| _| _| _| _| _| _| _| _|"
               "|_ |_ |_ |_ |_ |_ |_ |_ |_ ") ["222222222 ERR"]
    (ocr-lines " _  _  _  _  _  _  _  _  _ "
               " _| _| _| _| _| _| _| _| _|"
               " _| _| _| _| _| _| _| _| _|") ["333333333 ERR"]
    (ocr-lines ""
               "|_||_||_||_||_||_||_||_||_|"
               "  |  |  |  |  |  |  |  |  |") ["444444444 ERR"]
    (ocr-lines " _  _  _  _  _  _  _  _  _ "
               "|_ |_ |_ |_ |_ |_ |_ |_ |_ "
               " _| _| _| _| _| _| _| _| _|") ["555555555 ERR"]
    (ocr-lines " _  _  _  _  _  _  _  _  _ "
               "|_ |_ |_ |_ |_ |_ |_ |_ |_ "
               "|_||_||_||_||_||_||_||_||_|") ["666666666 ERR"]
    (ocr-lines " _  _  _  _  _  _  _  _  _ "
               "  |  |  |  |  |  |  |  |  |"
               "  |  |  |  |  |  |  |  |  |") ["777777777 ERR"]
    (ocr-lines " _  _  _  _  _  _  _  _  _ "
               "|_||_||_||_||_||_||_||_||_|"
               "|_||_||_||_||_||_||_||_||_|") ["888888888 ERR"]
    (ocr-lines " _  _  _  _  _  _  _  _  _ "
               "|_||_||_||_||_||_||_||_||_|"
               " _| _| _| _| _| _| _| _| _|") ["999999999 ERR"]
    (ocr-lines "    _  _     _  _  _  _  _ "
               "  | _| _||_||_ |_   ||_||_|"
               "  ||_  _|  | _||_|  ||_| _|") ["123456789"]
    (ocr-lines " _  _  _  _  _  _  _  _    "
               "| || || || || || || ||_   |"
               "|_||_||_||_||_||_||_| _|  |") ["000000051"]
    (ocr-lines "    _  _  _  _  _  _     _ "
               "|_||_|| || ||_   |  |  | _/"
               "  | _||_||_||_|  |  |  | _|") ["49006771? ILL"]
    (ocr-lines "    _  _     _  _  _  _  _ "
               "  | _| _||_|/_ |_   ||_||_|"
               "  ||_  _|  | _||_|  ||_| _/ ") ["1234?678? ILL"]
    (str (ocr-lines "    _  _     _  _  _  _  _ "
                    "  | _| _||_||_ |_   ||_||_|"
                    "  ||_  _|  | _||_|  ||_| _|")
         (ocr-lines " _  _  _  _  _  _  _  _  _ "
                    "| || || || || || || || || |"
                    "|_||_||_||_||_||_||_||_||_|")) ["123456789" "000000000"]))
