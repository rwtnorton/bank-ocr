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



(def digits

  "
    _  _     _  _  _  _  _ 
  | _| _||_||_ |_   ||_||_|
  ||_  _|  | _||_|  ||_| _|

    _  _  _  _  _  _     _ 
|_||_|| || ||_   |  |  ||_ 
  | _||_||_||_|  |  |  | _|

"
  )
