(ns towers.core
  (:require [goog.graphics :as graphics]
            [goog.dom :as dom]))

(defn log [& els]
  (.log js/console (apply pr-str els)))

(defn set-canvas []
  (let [cg (graphics/CanvasGraphics. 640 960)
        body (goog.dom/getElement "field")]
    (.render cg body)))

(set! (.-onload (.-body js/document)) set-canvas )
