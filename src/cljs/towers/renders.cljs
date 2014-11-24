(ns towers.renders
  (:require [towers.debug :as debug]
            [cljs.core.async :refer [chan >! <! put!]])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(defn render-grid [field renderer stage clicks]
  (debug/log "Rendering grid with " (count field) " items")
  (doseq [cell field]
    (let [rect (new js/PIXI.Graphics)]
      (.addChild stage rect)
      (set! (.-interactive rect) true)
      (set! (.-hitArea rect) (new js/PIXI.Rectangle (-> cell :pos :x) (-> cell :pos :y) (-> cell :d :w) (-> cell :d :h)))
      (set! (.-click rect) #(put! clicks cell)))))



(defn render-islands [islands renderer stage click-isl]
  (debug/log islands)
  ;; (doseq [isl-vec islands]
  ;;   (doseq [rec isl-vec]
  ;;     (let [isl-cell-pos (:cpos rec)
  ;;           rect-grp (new js/PIXI.Graphics)]
  ;;       (.rect context 
  ;;              (first rec)
  ;;              (last rec)
  ;;              (:w cell-d)
  ;;              (:h cell-d))
  ;;       (set! (.-fillStyle context) "black")
  ;;       (.fill context)))
  ;;   )
  )
