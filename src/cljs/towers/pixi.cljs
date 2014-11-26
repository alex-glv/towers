(ns towers.pixi)

(defn stage-add [el stage]
  (.addChild stage el))

(defn set-dim [el dim]
  (set! (.-height el) (:h dim))
  (set! (.-width el) (:w dim)))

(defn set-pos [el pos]
  (set! (.-x (.-position el)) (:x pos))
  (set! (.-y (.-position el)) (:y pos)))

(defn set-interactive
  ([el] (set-interactive el true))
  ([el state] (set! (.-interactive el) state)))

(defn get-texture-from-image [image-path]
  (new js/PIXI.Texture.fromImage image-path))

(defn get-sprite [texture]
  (new js/PIXI.Sprite texture))

(defn bootstrap [canvas-dim]
  (def render (atom {:renderer (.autoDetectRenderer js/PIXI (-> canvas-dim :w) (-> canvas-dim :h))
                     :stage (new js/PIXI.Stage 0xFFFFFF true)}))
  render)
