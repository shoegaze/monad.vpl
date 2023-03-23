(ns client.controller.draw.instance
  (:require [shared.node-cache.core :refer [get-node]]
            [shared.graph.core :refer [get-instances]]
            [client.controller.draw.util :refer [aabb circle pin-positions]]))


; HACK:
(def ?instance-frame-stroke-width 1.5)
(def ?instance-pin-stroke-width 1.5)
(def ?instance-pin-radius 10.0)


(defn- draw-instance-frame [ctx instance]
  (set! (.-fillStyle ctx)   "purple")
  (set! (.-strokeStyle ctx) "black")
  (set! (.-lineWidth ctx)   ?instance-frame-stroke-width)

  (let [{[dx dy] :translation
         [sx sy] :scale} (-> instance :view :transform)
        {[w h]   :size } (aabb instance)]
    (doto ctx
      .beginPath
      (.translate dx dy)
      (.scale sx sy)
      (.rect 0 0 w h)
      .fill
      .stroke
      (.setTransform 1 0 0
                     1 0 0)
      .closePath)))

(defn- draw-instance-icon [ctx instance node-cache]
  (set! (.-fillStyle ctx)    "black")
  (set! (.-textAlign ctx)    "center")
  (set! (.-textBaseline ctx) "middle")
  (set! (.-font ctx)         "24px sans-serif")

  (let [name (->> instance
                  :full-path
                  (get-node node-cache)
                  :model
                  :metadata
                  :name)
        {[dx dy] :translation
         [sx sy] :scale} (-> instance :view :transform)
        {[w h]   :size } (aabb instance)]
    (doto ctx
      .beginPath
      (.translate dx dy)
      (.scale sx sy)
      (.fillText name (/ w 2) (/ h 2))
      (.setTransform 1 0 0
                     1 0 0)
      .closePath)))

(defn- draw-instance-input [ctx input-pos]
  (set! (.-fillStyle ctx)   "aquamarine")
  (set! (.-strokeStyle ctx) "black")

  (let [[dx dy] input-pos]
    (doto ctx
      .beginPath
      (.translate dx dy)
      (circle 0 0 ?instance-pin-radius)
      .fill
      .stroke
      (.setTransform 1 0 0
                     1 0 0)
      .closePath)))

(defn- draw-instance-output [ctx output-pos]
  (set! (.-fillStyle ctx)   "orangered")
  (set! (.-strokeStyle ctx) "black")

  (let [[dx dy] output-pos]
    (doto ctx
      .beginPath
      (.translate dx dy)
      (circle 0 0 ?instance-pin-radius)
      .fill
      .stroke
      (.setTransform 1 0 0
                     1 0 0)
      .closePath)))

(defn- draw-instance-pins [ctx instance node-cache]
  (set! (.-lineWidth ctx) ?instance-pin-stroke-width)

  (let [{inputs  :inputs
         outputs :outputs} (pin-positions node-cache instance)]
    (doseq [input inputs]
      (draw-instance-input ctx input))

    (doseq [output outputs]
      (draw-instance-output ctx output))))

(defn- draw-instance [ctx instance node-cache]
  (draw-instance-frame ctx instance)
  (draw-instance-icon  ctx instance node-cache)
  (draw-instance-pins  ctx instance node-cache))

(defn draw-instances [ctx node-cache node-graph]
  (let [instances (get-instances node-graph)]
    (doseq [instance instances]
      (draw-instance ctx instance node-cache))))
