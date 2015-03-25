(ns jarm.main
  (:require [clojure.string :as string]
            [clojure.tools.cli :as cli])
  (:gen-class))

(def cli-opts
  [["-h" "--help"]])

(defn usage [opts-summary]
  (->> ["JAR Manager"
        ""
        "Usage: jarm [options] action"
        ""
        "Options:"
        opts-summary
        ""
        "Actions:"
        "  thing        Do a thing."
        "  other-thing  Do a different thing."
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
  (let [{:keys [options arguments errors summary]} (cli/parse-opts args cli-opts)]
    (cond
      (:help options) (exit 0 (usage summary))
      (not= (count arguments) 1) (exit 1 (usage summary))
      errors (exit 1 (error-msg errors)))
    (case (first arguments)
      "thing" (println ">> Thing!")
      "another-thing" (println ">> Another Thing!")
      (exit 1 (usage summary)))))
