(ns jarm.actions
  (:refer-clojure :exclude [list])
  (:require [clojure.string :as string]
            [jarm.repository :as repository]))

(defn- group->coordinates [g]
  (let [group-id (key g)
        all-artifacts (val g)
        shorthand (when (contains? all-artifacts group-id) group-id)
        artifacts (dissoc all-artifacts group-id)
        coordinates (map #(str group-id "/" (key %)) artifacts)]
    (if shorthand
      (cons shorthand coordinates)
      coordinates)))

(defn list [ctx args]
  (let [repo (repository/read-from-filesystem (:repository ctx))
        results (mapcat group->coordinates repo)]
    (println (string/join \newline results))))
