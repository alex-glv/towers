(ns towers.core
  (:require [goog.graphics :as graphics]
            [goog.dom :as dom]
            [goog.events :as events]
            [towers.debug :as debug]
            [towers.renders :as render]
            [towers.components :as components]))

(def canvas (graphics/CanvasGraphics. (-> components/field :dimensions :h) (-> components/field :dimensions :w)))

(defn set-canvas []
  (let [ body (dom/getElement "field")]
    (.render canvas body)))

(components/add-to components/renderables {:obj (:grid components/field)
                     :fn render/render-grid})
(components/add-to components/renderables {:obj components/islands
                     :fn render/render-islands})

(defn render-all []
  (doseq [r  @components/renderables]
    (let [obj (:obj r)
          fn (:fn r)]
      (fn obj canvas components/cell components/field))))

(defn handler []
  (set-canvas)
  (render-all))

(set! (.-onload (.-body js/document)) handler)
