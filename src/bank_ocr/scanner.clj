(ns bank-ocr.scanner
  (:require [clojure.string :as str])
  (:import [java.io StringReader BufferedReader]))

(defn normalize-chars
  [s]
  (when-not (nil? s)
    (str/replace s #"(?ms)[^_\s|]" "?")))

(defn string->rdr
  [s]
  (-> s (StringReader.) (BufferedReader.)))

(def rdr->lines line-seq)

(defn lines->line-groups
  [lines]
  (->> lines
       (partition-all 4)
       (map butlast)))

(defn string->line-groups
  [s]
  (if (nil? s)
    []
    (->> s
         string->rdr
         rdr->lines
         lines->line-groups)))

(defn rdr->line-groups
  [rdr]
  (if (nil? rdr)
    []
    (->> rdr
         rdr->lines
         lines->line-groups)))

;; A "line-group" is a seq of {\space underbar vbar} strings,
;; one per line.
(defn line-group->ocr-digits
  [line-group]
  (let [tokens (->> line-group
                    (map (fn [line]
                           (->> line
                                (format "%-27s")
                                (partition 3)
                                (map (partial apply str))))))]
    (if-not (seq tokens)
      []
      (->> tokens
           (apply map vector)
           (map #(str/join \newline %))))))


(comment

  (def ocr-digits "
    _  _     _  _  _  _  _ 
  | _| _||_||_ |_   ||_||_|
  ||_  _|  | _||_|  ||_| _|

    _  _  _  _  _  _     _ 
|_||_|| || ||_   |  |  ||_ 
  | _||_||_||_|  |  |  | _|

")
  (string->line-groups ocr-digits)

)
