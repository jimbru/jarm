(ns jarm.commands.list-test
  (:refer-clojure :exclude [list])
  (:require [clojure.string :as string]
            [clojure.test :refer :all]
            [jarm.commands.list :refer [list]]))

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
         (with-out-str (list test-context [])))))
