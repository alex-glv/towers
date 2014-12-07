(ns towers.core
  (:require [towers.debug :as debug]
            [towers.renders :as renders]
            [towers.components :as components]
            [domina.css :refer [sel]]
            [cljs.core.async :refer [chan >! <!]]
            [domina :as dom]
            [towers.pixi :as pixi]
            [towers.repl :as repl])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(defn render-all []
  (.render (:renderer @pixi/render) (:stage @pixi/render))
  (js/requestAnimFrame render-all))

(defn setup-elements []
  (let [render @pixi/render
        renderables @components/renderables]
    (.log js/console "Assets loaded")
    (doseq [rend renderables]
      (let [obj (:obj rend)
            func (:func rend)]
        (func obj (:stage render))))
    (render-all)))

(def canvas-dim (components/dimensions 960 640))
(def field-dim (components/dimensions 832 640))
(def grid-dim (components/dimensions 13 10))
(def field-cl (components/field field-dim grid-dim))

(defn teardown []
  (reset! components/renderables nil)
  (reset! pixi/render nil)
  (dom/destroy-children! (dom/by-id "field"))
  (dom/destroy-children! (dom/by-id "debug")))

(defn handler []
  (components/islands-map [(components/island '(1 1) '(1 2) '(1 3))
                (components/island '(7 8) '(7 9) '(8 8))
                (components/island '(3 5) '(4 5) '(5 5) '(6 5))] field-cl)

  (pixi/bootstrap canvas-dim)
  (dom/append! (dom/by-id "field") (.-view (:renderer @pixi/render)))
  (debug/log "Before loading assets...")
  (renders/load-assets (fn [] (towers.core/setup-elements))))

(set! (.-onload (.-body js/document)) handler)
