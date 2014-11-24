(ns towers.core
  (:require [towers.debug :as debug]
            [towers.renders :as render]
            [towers.components :as components]
            [domina.css :refer [sel]]
            [cljs.core.async :refer [chan >! <!]]
            [domina :as dom])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(defn setup-elements []
  (doseq [rend  @components/renderables]
    (let [obj (:obj rend)
          func (:fn rend)
          cl-ch (:ch rend)]
      (func obj (:renderer render) (:stage render) cl-ch))))

(defn render-all []
  (.render (:renderer render) (:stage render))
  (js/requestAnimFrame render-all))

(defn clicks-listener [channel]
  (go (while true
        (let [cell (<! channel)]
          (debug/log "Cell clicked:" (-> cell :cpos :col) " at " (-> cell :cpos :row))))))

(defn handler []
  (let [canvas-dimensions (components/dimensions 960 640)
        field-dimensions (components/dimensions 832 640)
        grid-dimensions (components/dimensions 13 10)
        field (components/field field-dimensions grid-dimensions)
        clicks (chan)
        clicks-isl (chan)
        islands (map (fn [isl] (components/attach-cell isl field)) components/islands)]
    (clicks-listener clicks)
    (def render {:renderer (.autoDetectRenderer js/PIXI (-> canvas-dimensions :w) (-> canvas-dimensions :h))
                 :stage (new js/PIXI.Stage 0xFFFFFF true)})
    (components/add-to components/renderables
                       {:obj field
                        :fn render/render-grid
                        :ch clicks})
    (components/add-to components/renderables
                       {:obj islands
                        :fn render/render-islands
                        :ch clicks-isl})
    (setup-elements)
    (dom/append! (dom/by-id "field") (.-view (:renderer render)))
    (render-all)))

(set! (.-onload (.-body js/document)) handler)
