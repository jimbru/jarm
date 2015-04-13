(ns jarm.repository-test
  (:require [clojure.test :refer :all]
            [jarm.repository :as repository]))

(def test-repo-directory "./test/.repository")

(def test-repo-result
  {"animal"       {"cat" #{"1.0.0"}
                   "dog" #{"1.2.0" "1.2.10"}}
   "plant"        {"plant" #{"2.0.1"}}
   "plant.flower" {"rose" #{"1.0.0"}}})

(deftest read-from-filesystem-test
  (let [res (repository/read-from-filesystem test-repo-directory)]
    (testing "correct result"
      (is (= test-repo-result res)))
    (testing "correct sort"
      (is (sorted? res))
      (is (sorted? (get res "animal"))))))
