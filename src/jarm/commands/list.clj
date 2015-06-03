(ns jarm.commands.list
  (:refer-clojure :exclude [list])
  (:require [com.palletops.cli.command :refer [def-command-fn]]
            [clojure.string :as string]
            [jarm.repository :as repository]))

(defn- coordinate-str [group-id artifact-id version]
  (let [version-str (format " (%s)" version)]
    (if (= group-id artifact-id)
      (str group-id version-str)
      (str group-id "/" artifact-id version-str))))

(defn- group->coordinates [g]
  (let [group-id (key g)
        all-artifacts (val g)
        shorthand (when-let [a (get all-artifacts group-id)]
                    (coordinate-str group-id group-id (first a)))
        artifacts (dissoc all-artifacts group-id)
        coords (map #(coordinate-str group-id (key %) (first (val %))) artifacts)]
    (if shorthand
      (cons shorthand coords)
      coords)))

(def-command-fn list
  "List installed jars."
  []
  []
  [context args]
  (let [repo (repository/read-from-filesystem (:repository context))
        results (mapcat group->coordinates repo)]
    (println (string/join \newline results))))