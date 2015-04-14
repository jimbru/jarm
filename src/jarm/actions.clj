(ns jarm.actions
  (:refer-clojure :exclude [list])
  (:require [clojure.string :as string]
            [jarm.repository :as repository]))

(defn list [ctx args]
  (let [repo (repository/read-from-filesystem (:repository ctx))
        all (mapcat (fn [g] (map #(str (key g) "/" (key %)) (val g))) repo)]
    (println (string/join \newline all))))
