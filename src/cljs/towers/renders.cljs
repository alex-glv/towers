(ns towers.renders
  (:require [goog.graphics :as graphics]
            [towers.debug :as debug]))

(defn render-grid [field canvas r]
  (let [context (.getContext canvas)]
    (debug/log "Rendering grid with " (count field) " items")
    (doseq [cell field]
      (debug/log cell)
      (.strokeRect context
             (-> cell :pos :x)
             (-> cell :pos :y)
             (-> cell :d :w)
             (-> cell :d :h))
      ;; (set! (.-fillStyle context) "black")
)))

(defn translate-cell-to-pos [cell cell-dim]
  (let [ret  [(* (first cell) (:w cell-dim)) (* (last cell) (:h cell-dim))]]
    ret))

(defn render-islands [obj canvas cell field]
  (doseq [isl-vec obj]
    (let [cell-d (:dimensions cell)
          rects (map #(translate-cell-to-pos %1 cell-d) isl-vec)
          context (.getContext canvas)]
      (doseq [rec rects]
        (.rect context 
               (first rec)
               (last rec)
               (:w cell-d)
               (:h cell-d))
        (set! (.-fillStyle context) "black")
        (.fill context)))))
