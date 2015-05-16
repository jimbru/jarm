(ns jarm.actions
  (:refer-clojure :exclude [list])
  (:require [clojure.string :as string]
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

(defn list [ctx args]
  (let [repo (repository/read-from-filesystem (:repository ctx))
        results (mapcat group->coordinates repo)]
    (println (string/join \newline results))))

(defn- parse-coordinate
  "Uses Leiningen-style coordinate specifications."
  [coord]
  (string/split coord #"/" 2))

(defn show [ctx args]
  (if (< (count args) 2)
    (do
      (println "Missing coordinate argument." \newline "Usage: jarm show <coordinate>")
      (System/exit 2))
    (let [coord (second args)
          artifact (parse-coordinate coord)
          repo (repository/read-from-filesystem (:repository ctx))
          versions (get-in repo artifact)]
      (if (set? versions)
        (println
          (as-> [] $
                (concat $ [coord
                           (str "Group ID:    " (first artifact))
                           (str "Artifact ID: " (second artifact))
                           "Versions:"])
                (concat $ (map (partial str "    ") versions))
                (string/join \newline $)))
        (do
          (println "Artifact not found.")
          (System/exit 2))))))
