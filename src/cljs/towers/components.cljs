(ns towers.components
  (:require [towers.debug :as debug]
            [towers.renders :as renders]))

(def renderables (atom []))

(def field-cells (atom []))

(defn renderable! [obj func]
  (swap! renderables conj {:obj obj :func func}))

(defn dimensions [h w]
  {:h h :w w})

(defn position [x y]
  {:x x :y y})

(defn cell-pos [col row]
  {:col col :row row})

(defn cell [h w x y ccol crow]
  {:d (dimensions h w)
   :pos (position x y)
   :cpos (cell-pos ccol crow)})

(defn get-cell [obj field]
  (let [col (:col obj)
        row (:row obj)
        cell (first (filter #(and (= col (-> % :cpos :col))
                                  (= row (-> % :cpos :row))) field))]
    cell))

;; fixme: add error handling when obj doesn't have cell-pos
(defn map-cell-coll [coll field]
  (map (fn [obj] (let [cell (get-cell obj field)]
                   {:cpos obj :cell cell}))
       coll))

(defn island [& vec]
  (doall (map (fn [cpos] (apply cell-pos cpos)) vec)))

(defn field [f-dimensions grid]
  (let [h (/ (:h f-dimensions) (:h grid))
        w (/ (:w f-dimensions) (:w grid))
        cells-total (* (:w grid) (:h grid))]
    (loop [n 1 cells []]
      (if (<= n cells-total)
        (let [mod-c (mod n (:w grid))
              col (if (= mod-c 0) (:w grid) mod-c)
              row (+ (int (/ (- n 1) (:w grid))) 1)
              x (* (- col 1) w)
              y (* (- row 1) h)]
          (recur (+ n 1) (conj cells (cell h w x y col row))))
        cells))))
;; todo: export islands definitions into levels

(defn islands-map [islands field]
  (doall (map (fn [island]
                (renderable! (map-cell-coll island field) renders/render-island))
              islands)))


(defn resource-slot [x y h w c]
  {:pos (position x y)
   :dim (dimensions h w)
   :contents c})
;; where c can be rendered


;; contents
;; related object
;;
;; player -> [ .. slots .. ]
;; slots -> [ (slot) (slot) ... ]
;; resources -> [ ..  ..  .. ]
;;
