(ns towers.components)

(defn add-to [to {:keys [obj fn]}]
  (swap! to conj {:obj obj :fn fn}))

(def field {:dimensions {:w 960
                         :h 640}
            :grid {:x 20
                   :y 40}})

(def cell {:dimensions {:w (/ (-> field :dimensions :w) (-> field :grid :x))
                        :h (/ (-> field :dimensions :h) (-> field :grid :y))}})


(def renderables (atom []))

;; todo: export islands definitions into levels
(def islands [['(1 1) '(1 2) '(1 3)] ['(7 8) '(7 9) '(8 8)]])
