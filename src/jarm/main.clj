(ns jarm.main
  (:require [clojure.string :as string]
            [clojure.tools.cli :as cli]
            [com.palletops.cli.command :refer [def-main]]
            [jarm.actions :as actions]
            [me.raynes.fs :as fs])
  (:gen-class))

(defonce default-repository "/usr/local/lib/jar")

(def-main
  {:project-name "Jarm"
   :self-name "jarm"
   :ns-prefixes ["jarm.commands."]}
  "I don't do a whole lot ... yet."
  [["-h" "--help"]
   ["-r" "--repository <path>" "Repository directory path." :default default-repository]])
