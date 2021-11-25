(ns blaster.clj-fstring-test
  (:require [clojure.test :refer :all]
            [blaster.clj-fstring :refer [f-str]]))

(deftest sum-test
  (is (f-str "{(+ 1 1)}") "2"))

(deftest escape-test
  (is (f-str "'{bar}") "{bar}"))

