(ns jarm.actions-test
  (:require [clojure.string :as string]
            [clojure.test :refer :all]
            [jarm.actions :as actions]))

(def test-repo-directory "./test/.repository")

(def test-repo-list
  (->> ["animal/cat (1.0.0)"
        "animal/dog (1.2.10)"
        "plant (2.0.1)"
        "plant/fern (5.4.0)"
        "plant.flower/rose (1.0.0)"
        ""]
       (string/join \newline)))

(deftest list-test
  (is (= test-repo-list
         (with-out-str (actions/list {:repository test-repo-directory} {})))))
