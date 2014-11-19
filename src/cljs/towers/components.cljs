(ns towers.components
  (:require [towers.debug :as debug]))

(def renderables (atom []))

(defn add-to [to {:keys [obj fn ch]}]
  (swap! to conj {:obj obj :fn fn :ch ch}))

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

(defn island [col row]
  {:cpos (cell-pos col row)})

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


;; todo: export islands definitions into levels
(def islands [[(island 1 1) (island 1 2) (island 1 3)]
              [(island 7 8) (island 7 9) (island 8 8)]
              [(island 14 20) (island 14 21) (island 14 22) (island 15 20) (island 15 21) (island 15 22)]])


