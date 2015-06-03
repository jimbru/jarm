(ns jarm.commands.show-test
  (:require [clojure.string :as string]
            [clojure.test :refer :all]
            [jarm.commands.show :refer [show]]))

(def test-context {:repository "./test/.repository"})

(def test-show-animal-dog
  (->> ["animal/dog"
        "Group ID:    animal"
        "Artifact ID: dog"
        "Versions:"
        "    1.2.10"
        "    1.2.0"
        "    1.2.10.2"
        ""]
       (string/join \newline)))

(deftest show-test
  (is (= test-show-animal-dog
         (with-out-str (show test-context ["animal/dog"])))))
