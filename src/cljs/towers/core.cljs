(ns towers.core
  (:require [goog.graphics :as graphics]
            [goog.events :as events]
            [towers.debug :as debug]
            [towers.renders :as render]
            [towers.components :as components]
            [domina.css :refer [sel]]
            [domina :as dom]))


;; (dmnvnts/listen! canvas
;;                  :mousemove
;;                  (fn [e] (let [trgt (.-target e)
;;                                x (- (.-clientX e) (.-offsetLeft trgt))
;;                                y (-  (.-clientY e) (.-offsetTop trgt))
;;                                upd (dom/getElement "cursors")]
;;                            (set! (.-innerHTML upd) (.join (array x "x" y) " ")))))

(defn render-all [renderer]
  (doseq [rend  @components/renderables]
    (let [obj (:obj rend)
          fn (:fn rend)
          r (:r rend)
          stage (new js/PIXI.Stage 0xFFFFFF)]
      (fn obj renderer stage r))))

(defn handler []
  (let [canvas-dimensions (components/dimensions 960 640)
        grid-dimensions (components/dimensions 40 20)
        renderer (.autoDetectRenderer js/PIXI (-> canvas-dimensions :w) (-> canvas-dimensions :h) nil true true)]
    (dom/append! (dom/by-id "field") (.-view renderer))
    (components/add-to components/renderables
                       {:obj (components/field canvas-dimensions grid-dimensions)
                        :fn render/render-grid})

    (components/add-to components/renderables
                       {:obj components/islands
                        :fn render/render-islands})
    (render-all renderer)))

(set! (.-onload (.-body js/document)) handler)
