(ns towers.renders
  (:require [goog.graphics :as graphics]
            [towers.debug :as debug]))

(defn- draw-a-line! [from-point-vec to-point-vec context]
  (.beginPath context)
  (.moveTo context (first from-point-vec) (last from-point-vec))
  (.lineTo context (first to-point-vec) (last to-point-vec))
  (.stroke context))

(defn render-grid [obj canvas]
  (let [lny (:y obj)
        lnx (:x obj)
        width (.-width canvas)
        height (.-height canvas)
        context (.getContext canvas)
        v-lines (.round js/Math (/ width lnx))
        h-lines (.round js/Math (/ height lny))]
    (debug/log "Drawing grid " lnx "x" lny)
    (loop [n 1]
      (if (or (= n (- lnx 1))
              (< n (- lnx 1)))
        (do
          (let [nth-line-end (* n v-lines)]
            (draw-a-line! [nth-line-end 0] [nth-line-end height] context))
          (recur (+ n 1)))))
    (loop [n 1]
      (if (or (= n (- lny 1))
              (< n (- lny 1)))
        (do
          (let [nth-line-end (* n h-lines)]
            (draw-a-line! [0 nth-line-end] [width nth-line-end] context))
          (recur (+ n 1)))))))


