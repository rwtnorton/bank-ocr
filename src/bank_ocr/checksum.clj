(ns bank-ocr.checksum)

(defn calculate
  [[d9 d8 d7 d6 d5 d4 d3 d2 d1 :as digits]]
  {:pre [(= 9 (count digits))
         (every? int? digits)
         (every? #(<= 0 % 9) digits)]}
  (rem (+' (* 9 d9)
           (* 8 d8)
           (* 7 d7)
           (* 6 d6)
           (* 5 d5)
           (* 4 d4)
           (* 3 d3)
           (* 2 d2)
           d1)
       11))
