(ns client.controller.draw
  (:require [taoensso.timbre :as timbre]
            [shared.node-cache.core :refer [get-node]]))


(defn- clear [ctx]
  (set! (.-fillStyle ctx) "blue")
  (let [canvas (.-canvas ctx)
        width  (.-width canvas)
        height (.-height canvas)]
    (.clearRect ctx 0 0 width height)))

; TODO: Take into account icon size, margin, & padding
; TODO: Take into account camera params ?
(defn- aabb [instance]
  (let [{translation :translation
         scale       :scale} (-> instance :view :transform)
        raw-size [100 100]]
    {:origin translation
     :size   (mapv * raw-size scale)}))

(defn- draw-instance-frame [ctx instance]
  (set! (.-fillStyle ctx) "purple")
  (set! (.-strokeStyle ctx) "black")

  (let [{[dx dy] :translation
         [sx sy] :scale} (-> instance :view :transform)
        {[w h] :size} (aabb instance)]
    (doto ctx
      (.translate dx dy)
      (.scale sx sy)
      (.rect 0 0 w h)
      ;.fill
      .stroke
      (.setTransform 1 0 0
                     1 0 0))))

(defn- draw-instance-icon [ctx instance node-cache]
  (set! (.-fillStyle ctx) "black")
  (set! (.-textAlign ctx) "center")
  (set! (.-textBaseline ctx) "middle")
  (set! (.-font ctx) "24px sans-serif")

  (let [name (->> instance
                  :full-path
                  (get-node node-cache)
                  :model
                  :metadata
                  :name)
        {[dx dy] :translation
         [sx sy] :scale} (-> instance :view :transform)
        {[w h] :size} (aabb instance)]
    (doto ctx
      (.translate dx dy)
      (.scale sx sy)
      (.fillText name (/ w 2) (/ h 2))
      (.setTransform 1 0 0
                     1 0 0))))

(defn- draw-instance [ctx instance node-cache]
  (draw-instance-frame ctx instance)
  ; TODO: Draw icon on top of frame
  (draw-instance-icon ctx instance node-cache)
  ; TODO:
  ;(draw-instance-pins ctx instance)
  )

(defn- draw-instances [ctx instances node-cache]
  (doseq [instance instances]
    (draw-instance ctx instance node-cache)))


(defn- draw-edge [ctx edge]
  :TODO)

(defn- draw-edges [ctx edges]
  (doseq [edge edges]
    (draw-edge ctx edge)))


(defn draw [node-cache node-graph ctx]
  (timbre/debug "Rendering canvas")

  (clear ctx)

  (let [{instances :instances
         edges     :edges} @node-graph]
    (draw-instances ctx instances @node-cache)
    ;(draw-edges     ctx edges)
    ))
