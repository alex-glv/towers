(ns towers.core
  (:require [towers.debug :as debug]
            [towers.renders :as render]
            [towers.components :as components]
            [domina.css :refer [sel]]
            [cljs.core.async :refer [chan >! <!]]
            [domina :as dom]
            [towers.pixi :as pixi])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(defn setup-elements [render]
  (doseq [rend  @components/renderables]
    (let [obj (:obj rend)
          func (:fn rend)
          cl-ch (:ch rend)]
      (func obj (:renderer render) (:stage render) cl-ch))))

(defn render-all [render]
  (.render (:renderer render) (:stage render))
  ;; (js/requestAnimFrame #(render-all render))
  )

(defn clicks-listener [channel]
  (debug/log "Setting clicks listener" channel)
  (go (while true
        (let [cell (<! channel)]
          (debug/log "Cell clicked:" (-> cell :cpos :col) " at " (-> cell :cpos :row))))))

(def canvas-dim (components/dimensions 960 640))
(def field-dim (components/dimensions 832 640))
(def grid-dim (components/dimensions 13 10))
(def field-cl (components/field field-dim grid-dim))
(def islands (map (fn [isl] (components/attach-cell isl field-cl)) components/islands))

(defn teardown []
  (reset! components/renderables nil)
  (reset! pixi/render nil)
  (dom/destroy-children! (dom/by-id "field"))
  (dom/destroy-children! (dom/by-id "debug")))

(defn handler []
  (let [clicks (chan)
        clicks-isl (chan)]
    (clicks-listener clicks)
    (pixi/bootstrap canvas-dim)
    (components/add-to components/renderables
                       {:obj field-cl
                        :fn render/render-grid
                        :ch clicks})
    (components/add-to components/renderables
                       {:obj islands
                        :fn render/render-islands
                        :ch clicks-isl})
    (setup-elements @pixi/render)
    (dom/append! (dom/by-id "field") (.-view (:renderer @pixi/render)))
    (render-all @pixi/render)))

(set! (.-onload (.-body js/document)) handler)
