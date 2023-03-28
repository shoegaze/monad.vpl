(ns client.controller.draw.camera
  (:require [client.controller.draw.util :refer [clamp]]))


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
        {[cx cy cz] :translation} camera
        ; Viewport center
        [cx cy]     (mapv + [cx cy] half-extent)]
    (doto ctx
      (.translate (+ cx) (+ cy))

      (.scale     (/ cz) (/ cz))
      (.translate (- cx) (- cy))

      (.translate (- cx) (- cy)))))

(defn translate [camera displacement]
  (let [{translation :translation
         aspect      :aspect
         near        :near
         far         :far        } camera
        translation*               (mapv + displacement translation)]
    (make-camera translation* aspect near far)))

(defn zoom [camera dz]
  (let [{translation :translation
         aspect      :aspect
         near        :near
         far         :far        } camera
        [dx dy dz*]                (mapv + [0 0 dz] translation)
        translation*               [dx dy (clamp dz* near far)]]
    (make-camera translation* aspect near far)))
