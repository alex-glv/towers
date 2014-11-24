(ns towers.renders
  (:require [towers.debug :as debug]
            [cljs.core.async :refer [chan >! <! put!]])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(defn stage-add [el stage]
  (.addChild stage el))

(defn set-dim [el dim]
  (set! (.-height el) (:h dim))
  (set! (.-width el) (:w dim)))

(defn set-pos [el pos]
  (set! (.-x (.-position el)) (:x pos))
  (set! (.-y (.-position el)) (:y pos)))

(defn set-interactive
  ([el] (set-interactive el true))
  ([el state] (set! (.-interactive el) state)))

(defn get-texture-from-image [image-path]
  (new js/PIXI.Texture.fromImage image-path))

(defn get-sprite [texture]
  (new js/PIXI.Sprite texture))

(defn render-grid [field renderer stage clicks]
  (debug/log "Rendering grid with " (count field) " items")
  (doseq [cell field]
    (let [rect (new js/PIXI.Graphics)]
      (.lineStyle rect 1 0x000000)
      (.drawRect rect (-> cell :pos :x) (-> cell :pos :y) (-> cell :d :w) (-> cell :d :h))
      (stage-add rect stage)
      (set-interactive rect)
      (set! (.-hitArea rect) (new js/PIXI.Rectangle (-> cell :pos :x) (-> cell :pos :y) (-> cell :d :w) (-> cell :d :h)))
      (set! (.-click rect) #(put! clicks cell)))))

(defn render-islands [islands renderer stage click-isl]
  (debug/log "rendering islands")
  (debug/log islands)
  (let [texture (get-texture-from-image "images/tile.png")]
    (doseq [isl islands]
      (doseq [island-map isl]
        (let [isl-img (get-sprite texture)]
          (set-interactive isl-img)
          (set-pos isl-img (-> island-map :cell :pos))
          (set-dim isl-img (-> island-map :cell :d))
          (stage-add isl-img stage))))))
