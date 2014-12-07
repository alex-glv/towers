(ns towers.renders
  (:require [towers.debug :as debug]
            [cljs.core.async :refer [chan >! <! put!]]
            [towers.pixi :as pixi])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(defn render-island [island stage]
  (let [texture (pixi/get-texture-from-frame "enclosed")]
    (.log js/console texture)
    (doseq [island-map island]
      (let [isl-img (pixi/get-sprite texture)]
        (pixi/set-interactive isl-img)
        (pixi/set-pos isl-img (-> island-map :cell :pos))
        (pixi/set-dim isl-img (-> island-map :cell :d))
        (pixi/stage-add isl-img stage)))))

(defn load-assets [onloadedfn]
  (let [assets (array "/images/tiles_sprites.json")
        loader (new js/PIXI.AssetLoader assets)]
    (.log js/console "in load-assets")
    (set! (.-onComplete loader) onloadedfn)
    (.load loader)
    ))
