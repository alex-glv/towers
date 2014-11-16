(ns towers.core
  (:require [goog.graphics :as graphics]
            [goog.dom :as dom]
            [towers.debug :as debug]
            [towers.renders :as render]))

(def field {:dimensions {:w 960
                         :h 640}
            :grid {:x 20
                   :y 40}})

(def cell {:dimensions {:w (/ (-> field :dimensions :w) (-> field :grid :x))
                        :h (/ (-> field :dimensions :h) (-> field :grid :y))}})

(def canvas (graphics/CanvasGraphics. (-> field :dimensions :h) (-> field :dimensions :w)))

(defn set-canvas []
  (let [ body (dom/getElement "field")]
    (.render canvas body)))

(def renderables (atom []))
(def islands [['(1 1) '(1 2) '(1 3)] ['(7 8) '(7 9) '(8 8)]])

(defn add-to [to {:keys [obj fn]}]
  (swap! to conj {:obj obj :fn fn}))

(add-to renderables {:obj (:grid field)
                     :fn render/render-grid})
(add-to renderables {:obj islands
                     :fn render/render-islands})

(defn render-all []
  (doseq [r (deref renderables)]
    (let [obj (:obj r)
          fn (:fn r)]
      (fn obj canvas cell field))))

(defn handler []
  (set-canvas)
  (render-all))

(set! (.-onload (.-body js/document)) handler)
