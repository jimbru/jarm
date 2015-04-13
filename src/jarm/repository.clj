(ns jarm.repository
  (:refer-clojure :exclude [nth])
  (:require [clojure.set :refer [union]]
            [clojure.string :as string]
            [ext.core :refer [nth update-in-default]]
            [me.raynes.fs :as fs])
  (:import java.util.regex.Pattern))

(defn- normalize-path [path]
  (.getAbsolutePath (fs/normalized path)))

(defn- strip-prefix [prefix s]
  (let [regex (re-pattern (str "^" (Pattern/quote prefix) "/"))]
    (string/replace s regex "")))

(defn- combine-group-id [components]
  (let [group-id (string/join "." (drop-last 2 components))
        artifact-id (nth components -2)
        version (last components)]
    [group-id artifact-id version]))

(defn- jar->coordinate [root-dir jar]
  (->> jar
       (strip-prefix root-dir)
       fs/split
       drop-last
       combine-group-id))

(defn read-from-filesystem
  "Builds a repository data structure from a Maven-style directory structure."
  [root-dir]
  (let [root-dir-norm (normalize-path root-dir)
        jars (fs/find-files root-dir-norm #".*\.jar")
        coords (map (partial jar->coordinate root-dir-norm) jars)]
    (reduce #(update-in-default %1 sorted-map (subvec %2 0 2) union #{(last %2)})
            (sorted-map)
            coords)))
