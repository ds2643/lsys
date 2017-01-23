(ns lsys.core
  (:gen-class)
  (:require [clojure.walk :refer [postwalk-replace]]
            [quil.core :as q]))

(defn specify-grammar
  "
  map of grammar specifying l-system
    v -> vector of variables (keywords)
    c -> vector of constants (keywords)
    s -> start (keyword)
    r -> map of rules (keywords mapped to vectors)
  "
  [v c s r] ;; add parameter for type? e.g., turtle
  {:pre [(vector? v)
         (reduce #(and %1 %2) (map keyword? v))
         (vector? c)
         (keyword? s)
         (.contains v s)
         (map? r)]}
  (array-map
       :variables v
       :constants c
       :start s
       :rules r))

(defn expand-term
  "
  outputs a vector specifying the lsystem after n iterations
    g -> grammar
    n -> number of iterations
  "
  [g n]
  {:pre [(map? g) (integer? n) (> n 0)]}
    (letfn [(aux [g n]
              (cond
                (zero? n) (:start g)
                :else (postwalk-replace (:rules g) (aux g (dec n)))))]
      (flatten (aux g n))))

;; quil component
(defn setup []
  (q/frame-rate 1) ;; 1 fps
  (q/background 200)) ;; grey bg

(defn grammar-map
  "Given a set of parameters,
  t -> symbol with associated meaning in context free grammar
  x -> x-coordinate
  y -> y-coordinate
  a -> initial angle, in radians
  l -> coordinate scale,
  return a coordinate, angle vector corresponding to the step dicated by turtle graphics"
  [t x y a l]
  (cond
    (= t :+) [x y (+ a (/ Math/PI 3))]
    (= t :-) [x y (- a (/ Math/PI 3))]
    :else [(+ x(* (q/cos a) l)) (+ x (* (q/sin a) l)) a]))

(defn generate-coordinates
  "Given a sequence of symbols indicating a recursive expansion, produce a set of coordinates renderable by turtle graphics"
  [xs l]
  (letfn
    [(aux [xs c acc l]
      (cond
        (empty? xs)
        acc
        :else
        (aux
          (rest xs)
          (grammar-map (first x) (first (first c)) (second (first c)) (second c) l)
          (cons (first c) acc))))] ;; current must include angle...
    (aux xs [[0 0], 0] '() l)))
i
(defn draw
  []
  nil)

(q/defsketch sierpinski-triangle
  :title "Sierpinski Triangle"
  :settings #(q/smooth 2) ;; anti-aliasing
  :setup setup
  :draw draw
  :size [323 200])

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
