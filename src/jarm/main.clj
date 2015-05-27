(ns jarm.main
  (:require [clojure.string :as string]
            [clojure.tools.cli :as cli]
            [jarm.actions :as actions]
            [me.raynes.fs :as fs])
  (:gen-class))

(defonce default-repository "/usr/local/lib/jar")

(def cli-opts
  [["-h" "--help"]
   ["-r" "--repository <path>" "Repository directory path."
    :default default-repository]])

(defn usage [opts-summary]
  (->> ["Jar Manager"
        ""
        "Usage: jarm [options] action"
        ""
        "Options:"
        opts-summary
        ""
        "Actions:"
        "  install  Install a jar."
        "  list     List installed jars."
        "  show     Show information about an installed jar."
        ""
        "Refer to the man page for more info."]
       (string/join \newline)))

(defn error-msg [errors]
  (str "The following errors occurred while parsing your command:\n\n"
       (string/join \newline errors)))

(defn exit [status msg]
  (println msg)
  (System/exit status))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [{:keys [options arguments errors summary]}
        (cli/parse-opts args cli-opts :in-order true)]
    (cond
      (:help options) (exit 0 (usage summary))
      errors (exit 1 (error-msg errors)))
    (case (first arguments)
      "install" (actions/install options arguments)
      "list" (actions/list options arguments)
      "show" (actions/show options arguments)
      (exit 1 "Unknown action. Run 'jarm -h' for help."))))
