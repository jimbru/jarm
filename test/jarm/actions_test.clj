(ns jarm.actions-test
  (:require [clojure.string :as string]
            [clojure.test :refer :all]
            [jarm.actions :as actions]))

(def test-context {:repository "./test/.repository"})

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
         (with-out-str (actions/list test-context {})))))

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
         (with-out-str (actions/show test-context ["show" "animal/dog"])))))
