(ns towers.components
  (:require [towers.debug :as debug]))

(defn add-to [to {:keys [obj fn]} & r]
  (swap! to conj {:obj obj :fn fn :r r}))

(defn dimensions [h w]
  {:h h :w w})

(defn position [x y]
  {:x x :y y})

(defn cell [h w x y]
  {:d (dimensions h w)
   :pos (position x y)})

(defn field [dimensions grid]
  (let [h (/ (:h dimensions) (:h grid))
        w (/ (:w dimensions) (:w grid))
        cells-total (* (:w grid) (:h grid))]
    (debug/log "w grid: " (:w grid))
    (debug/log "h grid: " (:h grid))
    (loop [n 1 cells []]
      (if (<= n cells-total)
        (let [x (* n w)
              y (* n h)]
          ;; fixme: it's all wrong
          (recur (+ n 1) (conj cells (cell h w x y))))
        cells))))


(def renderables (atom []))

;; todo: export islands definitions into levels
(def islands [['(1 1) '(1 2) '(1 3)]
              ['(7 8) '(7 9) '(8 8)]
              ['(14 20) '(14 21) '(14 22) '(15 20) '(15 21) '(15 22)]])


