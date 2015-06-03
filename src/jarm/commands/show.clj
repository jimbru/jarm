(ns jarm.commands.show
  (:require [com.palletops.cli.command :refer [def-command-fn]]
            [clojure.string :as string]
            [jarm.repository :as repository]))

(defn- parse-coordinate
  "Uses Leiningen-style coordinate specifications."
  [coord]
  (string/split coord #"/" 2))

(def-command-fn show
  "Show information about an installed jar."
  [["coordinate" "Artifact coordinate"]]
  []
  [context args]
  (let [coord (first args)
        artifact (parse-coordinate coord)
        repo (repository/read-from-filesystem (:repository context))
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
        (System/exit 2)))))
