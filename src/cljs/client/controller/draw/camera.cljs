(ns client.controller.draw.camera)


(defn make-camera [translation aspect near far]
  {:translation translation
   :aspect      aspect
   :near        near
   :far         far        })

(defn apply-transform [ctx camera]
  (let [canvas (.-canvas ctx)
        width  (.-width canvas)
        height (.-height canvas)
        half-extent (mapv #(/ % 2.0) [width height])
        ; Camera origin
        {[dx dy dz] :translation} camera
        ; Viewport center
        [cx cy]     (mapv + [dx dy] half-extent)]
    (doto ctx
      (.translate (+ cx) (+ cy))

      ; TODO: Scale ~ tan(dz) ?
      (.scale     (/ dz) (/ dz))
      (.translate (- dx) (- dy))

      (.translate (- cx) (- cy)))))
