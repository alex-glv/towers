(ns towers.core
  (:require [goog.graphics :as graphics]
            [goog.dom :as dom]
            [towers.debug :as debug]
            [towers.renders :as render]))

(def canvas (graphics/CanvasGraphics. 640 960))

(defn set-canvas []
  (let [ body (dom/getElement "field")]
    (.render canvas body)))

(def renderables (atom []))
(def game-map (atom []))
(def islands (atom []))

(defn add-renderable [& {:keys [obj fn]}]
  (swap! renderables conj {:obj obj :fn fn}))

(def grid {:x 20 :y 40})
(add-renderable :obj grid :fn render/render-grid)

(defn render-all []
  (doseq [r (deref renderables)]
    (let [obj (:obj r)
          fn (:fn r)]
      (fn obj canvas))))

(defn handler []
  (set-canvas)
  (render-all))

(set! (.-onload (.-body js/document)) handler)
