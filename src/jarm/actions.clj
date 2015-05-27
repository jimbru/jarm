(ns jarm.actions
  (:refer-clojure :exclude [list])
  (:require [cemerick.pomegranate.aether :as aether]
            [clojure.string :as string]
            [jarm.repository :as repository])
  (:import [org.sonatype.aether.resolution DependencyResolutionException]))

(defn- exit-coordinate-arg [action]
  (println "Missing coordinate argument." \newline "Usage: jarm " action " <coordinate>")
  (System/exit 2))

(def repositories
  [["central" {:url "https://repo1.maven.org/maven2/" :snapshots false}]
   ["clojars" {:url "https://clojars.org/repo/"}]])

(defn install [ctx args]
  (if (= (count args) 3)
    (try
      (let [artifact (second args)
            version (nth args 2)
            coord [(symbol artifact) version]]
        (aether/resolve-dependencies
          :local-repo (:repository ctx)
          :repositories repositories
          :coordinates [coord]
          :transfer-listener :stdout)
        (println "Installed" coord))
      (catch DependencyResolutionException e
        (println (.getMessage e))))
    (exit-coordinate-arg "install")))

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
    (exit-coordinate-arg "show")
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
