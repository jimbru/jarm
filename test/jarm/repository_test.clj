(ns jarm.repository-test
  (:require [clojure.set :refer [union]]
            [clojure.test :refer :all]
            [jarm.repository :as repository]))

(deftest semver-set-test
  (testing "set created with keys"
    (let [s (repository/semver-set)]
      (is (set? s))
      (is (sorted? s))
      (is (empty? s)))
    (let [s (repository/semver-set "1.0.0" "0.9.0" "0.9.1" "0.9.0-SNAPSHOT")]
      (is (set? s))
      (is (sorted? s))
      (is (= ["1.0.0" "0.9.1" "0.9.0" "0.9.0-SNAPSHOT"] (seq s)))))
  (testing "keys unioned later"
    (let [s (-> (repository/semver-set)
                (union (repository/semver-set "1.0.0"))
                (union (repository/semver-set "0.9.0"))
                (union (repository/semver-set "0.9.1"))
                (union (repository/semver-set "0.9.0-SNAPSHOT")))]
      (is (set? s))
      (is (sorted? s))
      (is (= ["1.0.0" "0.9.1" "0.9.0" "0.9.0-SNAPSHOT"] (seq s))))))

(def test-repo-directory "./test/.repository")

(def test-repo-result
  {"animal"       {"cat" #{"1.0.0"}
                   "dog" #{"1.2.0" "1.2.10" "1.2.10.2"}}
   "plant"        {"fern" #{"5.4.0"}
                   "plant" #{"2.0.1"}}
   "plant.flower" {"rose" #{"1.0.0"}}})

(deftest read-from-filesystem-test
  (let [res (repository/read-from-filesystem test-repo-directory)]
    (testing "correct result"
      (is (= test-repo-result res)))
    (testing "correct sort"
      (is (sorted? res))
      (is (sorted? (get res "animal")))
      (is (= ["1.2.10" "1.2.0" "1.2.10.2"] (seq (get-in res ["animal" "dog"])))))))
