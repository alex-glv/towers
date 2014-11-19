(ns towers.renders
  (:require [towers.debug :as debug]
            [cljs.core.async :refer [chan >! <! put!]])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(defn render-grid [field renderer stage clicks]
  (debug/log "Rendering grid with " (count field) " items")
  (doseq [cell field]
    (let [rect (new js/PIXI.Graphics)]
      (.lineStyle rect 1 0x000000)
      (.drawRect rect (-> cell :pos :x) (-> cell :pos :y) (-> cell :d :w) (-> cell :d :h))
      (.addChild stage rect)
      (set! (.-interactive rect) true)
      (set! (.-hitArea rect) (new js/PIXI.Rectangle (-> cell :pos :x) (-> cell :pos :y) (-> cell :d :w) (-> cell :d :h)))
      (set! (.-click rect) #(put! clicks cell)))))

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
