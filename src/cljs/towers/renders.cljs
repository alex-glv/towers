(ns towers.renders
  (:require [towers.debug :as debug]
            [cljs.core.async :refer [chan >! <! put!]])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(defn render-grid [field renderer stage clicks]
  (debug/log "Rendering grid with " (count field) " items")
  (doseq [cell field]
    (let [rect (new js/PIXI.Graphics)]
      ;; (.lineStyle rect 1 0x000000)
      ;; (.drawRect rect (-> cell :pos :x) (-> cell :pos :y) (-> cell :d :w) (-> cell :d :h))
      (.addChild stage rect)
      (set! (.-interactive rect) true)
      (set! (.-hitArea rect) (new js/PIXI.Rectangle (-> cell :pos :x) (-> cell :pos :y) (-> cell :d :w) (-> cell :d :h)))
      (set! (.-click rect) #(put! clicks cell)))))


(defn render-islands [islands renderer stage click-isl]
  (debug/log "rendering islands")
  (debug/log islands)
  (let [texture (new js/PIXI.Texture.fromImage "images/tile.png")]
    (.log js/console texture)
    (doseq [isl islands]
      (doseq [island-map isl]
        (let [isl-img (new js/PIXI.Sprite texture)
              x (-> island-map :cell :pos :x)
              y (-> island-map :cell :pos :y)
              w (-> island-map :cell :d :w)
              h (-> island-map :cell :d :h)]
          (set! (.-renderable isl-img) true)
          ;; (set! (.-x (.-anchor isl-img)) 0.5)
          ;; (set! (.-y (.-anchor isl-img)) 0.5)
          (set! (.-interactive isl-img) true)
          (set! (.-x (.-position isl-img)) x)
          (set! (.-y (.-position isl-img)) y)
          (set! (.-width isl-img) w)
          (set! (.-height isl-img) h)
          (.log js/console isl-img)
          (.addChild stage isl-img))))))
