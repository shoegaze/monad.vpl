(ns client.controller.draw.edge
  (:require [shared.graph.core :refer [get-instance]]
            [client.controller.draw.util :refer [pin-positions]]))


; HACK:
(def ?edge-width 4.0)


; Draws the edge curve given two canvas-space points
(defn- draw-edge-curve [ctx [x-1 y-1] [x-2 y-2]]
  (let [gradient (.createLinearGradient ctx x-1 y-1 x-2 y-2)]
    (doto gradient
      (.addColorStop 0.0 "orangered")
      (.addColorStop 1.0 "aquamarine"))
    (set! (.-strokeStyle ctx) gradient))
  (set! (.-lineWidth ctx) ?edge-width)

  (doto ctx
    .beginPath
    (.moveTo x-1 y-1)
    (.lineTo x-2 y-2)
    .stroke
    .closePath))

(defn- draw-edge [ctx node-cache node-graph edge]
  (let [{out-id  :out-instance-id
         out-pin :out-pin
         in-id   :in-instance-id
         in-pin  :in-pin} edge

        out-instance        (get-instance  node-graph out-id)
        {out-pins :outputs} (pin-positions node-cache out-instance)
        out-pin-position    (get out-pins out-pin)

        in-instance         (get-instance  node-graph in-id)
        {in-pins  :inputs } (pin-positions node-cache in-instance)
        in-pin-position     (get in-pins in-pin)]
    (draw-edge-curve ctx out-pin-position in-pin-position)))

(defn draw-edges [ctx node-cache node-graph]
  (let [edges (:edges node-graph)]
    (doseq [edge edges]
      (draw-edge ctx node-cache node-graph edge))))
