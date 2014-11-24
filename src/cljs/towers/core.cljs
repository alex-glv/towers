(ns towers.core
  (:require [towers.debug :as debug]
            [towers.renders :as render]
            [towers.components :as components]
            [domina.css :refer [sel]]
            [cljs.core.async :refer [chan >! <!]]
            [domina :as dom])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(defn render-all [renderer stage]
  (doseq [rend  @components/renderables]
    (let [obj (:obj rend)
          fn (:fn rend)
          cl-ch (:ch rend)]
      (fn obj renderer stage cl-ch)))
  (debug/log "Rendering")
  (set! (.-interactive stage) true)
  (.render renderer stage))

(defn clicks-listener [channel]
  (go (while true
        (let [cell (<! channel)]
          (debug/log "Cell clicked:" (-> cell :cpos :col) " at " (-> cell :cpos :row))))))

(defn handler []
  (let [canvas-dimensions (components/dimensions 960 640)
        field-dimensions (components/dimensions 832 640)
        grid-dimensions (components/dimensions 13 10)
        renderer (.autoDetectRenderer js/PIXI (-> canvas-dimensions :w) (-> canvas-dimensions :h) nil true true)
        stage (new js/PIXI.Stage 0xFFFFFF)
        clicks (chan)
        clicks-isl (chan)
                          ;; (components/add-to components/renderables
                          ;;                    {:obj components/islands
                          ;;                     :fn render/render-islands
                          ;;                     :ch clicks-isl})
        ]
    
    (clicks-listener clicks)
    (dom/append! (dom/by-id "field") (.-view renderer))
    (components/add-to components/renderables
                       {:obj (components/field field-dimensions grid-dimensions)
                        :fn render/render-grid
                        :ch clicks})
    (render-all renderer stage)))
(set! (.-onload (.-body js/document)) handler)
