(ns jarm.actions-test
  (:require [clojure.string :as string]
            [clojure.test :refer :all]
            [jarm.actions :as actions]))

(def test-repo-directory "./test/.repository")

(def test-repo-list
  (->> ["animal/cat"
        "animal/dog"
        "plant"
        "plant/fern"
        "plant.flower/rose"
        ""]
       (string/join \newline)))

(deftest list-test
  (is (= test-repo-list
         (with-out-str (actions/list {:repository test-repo-directory} {})))))
