(ns towers.components
  (:require [towers.debug :as debug]))

(defn add-to [to {:keys [obj fn]} & r]
  (swap! to conj {:obj obj :fn fn :r r}))

(defn dimensions [h w]
  {:h h :w w})

(defn position [x y]
  {:x x :y y})

(defn cell-pos [col row]
  {:col col :row row})

(defn cell [h w x y ccol crow]
  {:d (dimensions h w)
   :pos (position x y)
   :cpos (cell-pos ccol crow)})

(defn field [f-dimensions grid]
  (let [h (/ (:h f-dimensions) (:h grid))
        w (/ (:w f-dimensions) (:w grid))
        cells-total (* (:w grid) (:h grid))]
    (loop [n 1 cells []]
      (if (<= n cells-total)
        (let [mod-c (mod n (:w grid))
              col (if (= mod-c 0) (:w grid) mod-c)
              row (+ (int (/ (- n 1) (:w grid))) 1)
              x (* (- col 1) w)
              y (* (- row 1) h)]
          (recur (+ n 1) (conj cells (cell h w x y col row))))
        cells))))


(def renderables (atom []))

;; todo: export islands definitions into levels
(def islands [['(1 1) '(1 2) '(1 3)]
              ['(7 8) '(7 9) '(8 8)]
              ['(14 20) '(14 21) '(14 22) '(15 20) '(15 21) '(15 22)]])


