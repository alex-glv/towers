(ns towers.components)

(defn add-to [to {:keys [obj fn]}]
  (swap! to conj {:obj obj :fn fn}))

(defn dimensions [h w]
  {:h h :w w})

(defn position [x y]
  {:x x :y y})

(def field {:dimensions {:h 960
                         :w 640}
            :grid {:x 20
                   :y 40}})

(def cell {:dimensions {:w (/ (-> field :dimensions :w) (-> field :grid :x))
                        :h (/ (-> field :dimensions :h) (-> field :grid :y))}})

(def renderables (atom []))

;; todo: export islands definitions into levels
(def islands [['(1 1) '(1 2) '(1 3)]
              ['(7 8) '(7 9) '(8 8)]
              ['(14 20) '(14 21) '(14 22) '(15 20) '(15 21) '(15 22)]])
