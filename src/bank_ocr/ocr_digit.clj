(ns bank-ocr.ocr-digit
  (:require [clojure.string :as str]))

(def ^:constant zero
  (str/join \newline [" _ "
                      "| |"
                      "|_|"]))
(def ^:constant one
  (str/join \newline ["   "
                      "  |"
                      "  |"]))
(def ^:constant two
  (str/join \newline [" _ "
                      " _|"
                      "|_ "]))
(def ^:constant three
  (str/join \newline [" _ "
                      " _|"
                      " _|"]))
(def ^:constant four
  (str/join \newline ["   "
                      "|_|"
                      "  |"]))
(def ^:constant five
  (str/join \newline [" _ "
                      "|_ "
                      " _|"]))
(def ^:constant six
  (str/join \newline [" _ "
                      "|_ "
                      "|_|"]))
(def ^:constant seven
  (str/join \newline [" _ "
                      "  |"
                      "  |"]))
(def ^:constant eight
  (str/join \newline [" _ "
                      "|_|"
                      "|_|"]))
(def ^:constant nine
  (str/join \newline [" _ "
                      "|_|"
                      " _|"]))

(def ocr-digit-for [zero one two three four five six seven eight nine])

(def ^:constant blank
  (str/join \newline ["   "
                      "   "
                      "   "]))
