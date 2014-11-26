(ns towers.renders
  (:require [towers.debug :as debug]
            [cljs.core.async :refer [chan >! <! put!]]
            [towers.pixi :as pixi])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(defn render-island [island stage]
  (let [texture (pixi/get-texture-from-image "images/tile.png")]
    (doseq [island-map island]
      (let [isl-img (pixi/get-sprite texture)]
        (pixi/set-interactive isl-img)
        (pixi/set-pos isl-img (-> island-map :cell :pos))
        (pixi/set-dim isl-img (-> island-map :cell :d))
        (pixi/stage-add isl-img stage)))))
