(ns client.controller.draw
  (:require [taoensso.timbre :as timbre]))


(defn- draw-instance-frame [ctx instance]
  (let [[x y] (-> instance
                  :view
                  :transform
                  :translation)]
    (set! (.-fillStyle ctx) "rgb(255,0,0)")
    (doto ctx
      (.rect x y 100 100)
      .fill)))

(defn- draw-instance [ctx instance]
  (draw-instance-frame ctx instance)
  ;; TODO:
  ;(draw-instance-icon ctx instance)
  ;(draw-instance-inputs ctx instance)
  ;(draw-instance-outputs ctx instance)
  )

(defn- draw-instances [ctx instances]
  (doseq [instance instances]
    (draw-instance ctx instance)))


(defn- draw-edge [ctx edge]
  :TODO)

(defn- draw-edges [ctx edges]
  (doseq [edge edges]
    (draw-edge ctx edge)))


(defn draw [_node-cache node-graph ctx]
  (timbre/debug "Rendering canvas")

  (let [{instances :instances
         edges     :edges} @node-graph]
    (draw-instances ctx instances)
    ;(draw-edges     ctx edges)
    ))
