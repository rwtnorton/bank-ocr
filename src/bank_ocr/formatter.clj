(ns bank-ocr.formatter
  (:require [bank-ocr.analyzer :as analyzer]
            [clojure.string :as str]))

(defmulti format-analysis (juxt :ok :error))

(defmethod format-analysis :default
  [& _args]
  (throw (IllegalStateException.)))

(defmethod format-analysis [nil ::analyzer/invalid-input]
  [_analysis]
  "*** INVALID INPUT ***")

(defmethod format-analysis [nil ::analyzer/illegal-char]
  [{:keys [parsed] :as analysis}]
  (format "%9s ILL"
          (str/join (map #(if (int? %)
                            (str %)
                            "?")
                         parsed))))

(defmethod format-analysis [nil ::analyzer/bad-checksum]
  [{:keys [parsed] :as analysis}]
  (format "%9s ERR"
          (str/join (map str parsed))))

(defmethod format-analysis [true nil]
  [{:keys [parsed] :as analysis}]
  (format "%9s"
          (str/join (map str parsed))))
