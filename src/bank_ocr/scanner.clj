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
  (->> (iterate (fn [[group vs :as args]]
                  (if (seq vs)
                    (let [[t d] (split-with #(not (re-seq #"^\s*$" %)) vs)]
                      [(conj group t) (rest d)])
                    args))
                [[] lines])
       (drop-while (comp seq second))
       ffirst
       (remove empty?)))

(defn string->line-groups
  [s]
  (if (nil? s)
    []
    (->> s
         string->rdr
         rdr->lines
         lines->line-groups)))

;; A "line-group" is a seq of {\space underbar vbar} strings,
;; one per line.
(defn line-group->ocr-digits
  [line-group]
  (let [tokens (->> line-group
                    (map (fn [line]
                           (->> line
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
