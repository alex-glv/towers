(ns towers.core
  (:require [goog.graphics :as graphics]
            [goog.dom :as dom]
            [towers.debug :as debug]))

(def canvas (graphics/CanvasGraphics. 640 960))

(defn set-canvas []
  (let [ body (dom/getElement "field")]
    (.render canvas body)))

(defn render-grid [lnx lny canvas]
  (let [width (.-width canvas)
        height (.-height canvas)
        context (.getContext canvas)
        v-lines (.round js/Math (/ width lnx))
        h-lines (.round js/Math (/ height lny))]
    (debug/log "test 123")
    (loop [n 1]
      (if (or (= n (- lnx 1))
              (< n (- lnx 1)))
        (do 
          (.beginPath context)
          (.moveTo context (* n v-lines) 0)
          (.lineTo context (* n v-lines) height)
          (.stroke context)
          (recur (+ n 1)))) )
    (loop [n 1]
      (if (or (= n (- lny 1))
              (< n (- lny 1)))
        (do
          (.beginPath context)
          (.moveTo context 0 (* n h-lines))
          (.lineTo context width (* n h-lines))
          (.stroke context)
          (recur (+ n 1)))))))

;; (defmacro draw-line [type]
;;   (if (= type :horizontal)
;;     `(loop [n 1]
;;        (if (or (= n (-lny 1))
;;                (< n (-lny 1)))))))

(defn handler []
  (set-canvas)
  (render-grid 20 40 canvas))

(set! (.-onload (.-body js/document)) handler)
