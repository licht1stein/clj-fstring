(ns blaster.clj-fstring-test
  (:require [clojure.test :refer :all]
            [blaster.clj-fstring :refer [f-string]]))

(deftest a-test
  (is (f-string "{(+ 1 1)}") "2"))
