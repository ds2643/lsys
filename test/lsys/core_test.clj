(ns lsys.core-test
  (:require [clojure.test :refer :all]
            [lsys.core :refer :all]))

(deftest test-specify-grammar
  (testing "specify grammar builds a grammar map given the appropriate set of inputs")
    (let [variables [:A :B]
          constants [:+]
          start :A
          rules {:A [:A :+ :B]
                 :B [:A]}
          expected {:variables [:A :B]
                   :constants [:+]
                   :start :A
                   :rules {:A [:A :+ :B]
                           :B [:A]}}
          observed (specify-grammar variables constants start rules)]
            (is (= expected observed))))

(deftest test-expand-term
  (testing "expand-term expands from starting point recursively given a grammar and number of iterations")
    (let [grammar {:variables [:A :B]
                   :constants [:+]
                   :start :A
                   :rules {:A [:A :+ :B]
                           :B [:A]}}
          iterations 3 ;; arbitrarily chosen
          expected [:A :+ :B :+ :A :+ :A :+ :B]
          observed (expand-term grammar iterations)]
    (is (= expected observed))))

(deftest test-grammar-map-delta-angle
  (testing "given a symbol indicating a change in angle, grammar-map produces the appropriate change in angle while displacement remains unchanged")
  (let [t :+
        x 0
        y 0
        a 0
        l 1
        observed (grammar-map t x y a l)
        expected [[0 0] (/ 3 Math/PI)]]
    (is (= expected observed))))
(deftest test-grammar-map-displacement
  (testing "given a symbol indicating a step forward on a cartesean plane, grammar-map produces the appropriate change coordinates while angle remains unchanged")
  (let [t :a
        x 0
        y 0
        a 0
        l 1
        observed (grammar-map t x y a l)
        expected [[0 1] 0]]
    (is (= expected observed))))

(deftest test-generate-coordinates
  (testing "...")
  (let [parameter-seq [:a :b :+ :a :b :- :a :b]
        length 1
        observed (generate-coordinates parameter-seq length)
        expected [[0 0] [0 1] [0 1] [] [] [] [] []]] ;; TODO: calculate coordinates by hand
    (is (= expected observed))))
