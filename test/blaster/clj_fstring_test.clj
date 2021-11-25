(ns blaster.clj-fstring-test
  (:require [clojure.test :refer :all]
            [blaster.clj-fstring :refer [f-string]]))

(deftest sum-test
  (is (f-string "{(+ 1 1)}") "2"))

(deftest escape-test
  (is (f-string "'{bar}") "{bar}"))

(deftest mismatch-test
  (is (thrown? clojure.lang.ExceptionInfo (f-string "{")))
  (is (thrown? clojure.lang.ExceptionInfo (f-string "}"))))


(defn foo [x]
  (inc x))

(comment
  (foo 1)
  (list 1 2) := '(1 2)
  
  )
