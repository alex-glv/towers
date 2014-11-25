(ns towers.renders
  (:require [towers.debug :as debug]
            [cljs.core.async :refer [chan >! <! put!]]
            [towers.pixi :as pixi])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(defn render-grid [field renderer stage clicks]
  (debug/log "Rendering grid with " (count field) " items")
  (doseq [cell field]
    (let [rect (new js/PIXI.Graphics)]
      (pixi/stage-add rect stage)
      (pixi/set-interactive rect)
      (set! (.-hitArea rect) (new js/PIXI.Rectangle (-> cell :pos :x) (-> cell :pos :y) (-> cell :d :w) (-> cell :d :h)))
      (set! (.-click rect) #(put! clicks cell)))))

(defn render-islands [islands renderer stage click-isl]
  (debug/log "rendering islands")
  (let [texture (pixi/get-texture-from-image "images/tile.png")]
    (doseq [isl islands]
      (doseq [island-map isl]
        (let [isl-img (pixi/get-sprite texture)]
          (pixi/set-interactive isl-img)
          (pixi/set-pos isl-img (-> island-map :cell :pos))
          (pixi/set-dim isl-img (-> island-map :cell :d))
          (pixi/stage-add isl-img stage))))))
