(ns towers.core
  (:require [goog.graphics :as graphics]
            [goog.dom :as dom]
            [goog.events :as events]
            [towers.debug :as debug]
            [towers.renders :as render]
            [towers.components :as components]
            [domina.events :as dmnvnts]))


;; (dmnvnts/listen! canvas
;;                  :mousemove
;;                  (fn [e] (let [trgt (.-target e)
;;                                x (- (.-clientX e) (.-offsetLeft trgt))
;;                                y (-  (.-clientY e) (.-offsetTop trgt))
;;                                upd (dom/getElement "cursors")]
;;                            (set! (.-innerHTML upd) (.join (array x "x" y) " ")))))

(defn render-all [canvas]
  (doseq [rend  @components/renderables]
    (let [obj (:obj rend)
          fn (:fn rend)
          r (:r rend)]
      (fn obj canvas r))))

(defn handler []
  (let [canvas-dimensions (components/dimensions 960 640)
        grid-dimensions (components/dimensions 40 20)
        canvas (graphics/CanvasGraphics. (-> canvas-dimensions :w) (-> canvas-dimensions :h))
        body (dom/getElement "field")]
    (.render canvas body)
    (components/add-to components/renderables
                       {:obj (components/field canvas-dimensions grid-dimensions)
                        :fn render/render-grid})

    (components/add-to components/renderables
                       {:obj components/islands
                        :fn render/render-islands})
    (render-all canvas)))

(set! (.-onload (.-body js/document)) handler)
