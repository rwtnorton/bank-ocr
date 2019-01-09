(ns bank-ocr.scanner
  (:require [clojure.java.io :as io]
            [clojure.set :as set])
  (:import [java.io StringReader BufferedReader]))

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

(def string->line-groups (comp lines->line-groups
                               rdr->lines
                               string->rdr))



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
