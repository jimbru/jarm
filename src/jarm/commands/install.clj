(ns jarm.commands.install
  (:require [cemerick.pomegranate.aether :as aether]
            [com.palletops.cli.command :refer [def-command-fn]])
  (:import [org.sonatype.aether.resolution DependencyResolutionException]))

(def repositories
  [["central" {:url "https://repo1.maven.org/maven2/" :snapshots false}]
   ["clojars" {:url "https://clojars.org/repo/"}]])

(def-command-fn install
  "Install a jar."
  [["coordinate" "Artifact coordinate"]
   ["version" "Artifact version"]]
  []
  [context args]
  (try
    (let [artifact (first args)
          version (second args)
          coord [(symbol artifact) version]]
      (aether/resolve-dependencies
        :local-repo (:repository context)
        :repositories repositories
        :coordinates [coord]
        :transfer-listener :stdout)
      (println "Installed" coord))
    (catch DependencyResolutionException e
      (println (.getMessage e)))))
