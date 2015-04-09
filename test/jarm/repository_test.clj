(ns jarm.repository-test
  (:require [clojure.test :refer :all]
            [jarm.repository :as repository]))

(def test-repo-directory "./test/.repository")

(def test-repo-result
  {"animal" {"cat" #{"1.0.0"}
             "dog" #{"1.2.0" "1.2.10"}}
   "plant" {"plant" #{"2.0.1"}}})

(deftest read-from-filesystem-test
  (is (= test-repo-result
         (repository/read-from-filesystem test-repo-directory))))
