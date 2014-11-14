(ns towers.core
  (:require [goog.graphics :as graphics]
            [goog.dom :as dom]))

(defn log [& els]
  (.log js/console (apply pr-str els)))

(defn set-canvas []
  (let [cg (graphics/CanvasGraphics. 640 960)
        body (goog.dom/getElement "field")]
    (.render cg body)))

(defn handler []
  (set-canvas))

(defn render-grid [sx sy]
  (let [v-lines (.round js/Math (/ sx 10))
        h-lines (.round js/Math (/ sy 20))]
    ))

(set! (.-onload (.-body js/document)) handler)
