(ns towers.renders
  (:require [goog.graphics :as graphics]
            [towers.debug :as debug]))

(defn render-grid [field renderer stage r]
  (debug/log "Rendering grid with " (count field) " items")
  (doseq [cell field]
    (debug/log cell)
    (let [rect (new js/PIXI.Graphics)]
      (.lineStyle rect 1 0x000000)
      (.drawRect rect (-> cell :pos :x) (-> cell :pos :y) (-> cell :d :w) (-> cell :d :h))
      (.addChild stage rect)
      (.render renderer stage))
    ))

(defn render-islands [obj canvas cell field]
  ;; (doseq [isl-vec obj]
  ;;   (let [cell-d (:dimensions cell)
  ;;         rects (map #(translate-cell-to-pos %1 cell-d) isl-vec)
  ;;         context (.getContext canvas)]
  ;;     (doseq [rec rects]
  ;;       (.rect context 
  ;;              (first rec)
  ;;              (last rec)
  ;;              (:w cell-d)
  ;;              (:h cell-d))
  ;;       (set! (.-fillStyle context) "black")
  ;;       (.fill context))))
  )
