(ns jarm.actions
  (:refer-clojure :exclude [list])
  (:require [clojure.pprint :refer [pprint]]
            [jarm.repository :as repository]))

(defn list [ctx args]
  (let [repo (repository/read-from-filesystem (:repository ctx))]
    (pprint repo)))
