(ns client.controller.draw.util
  (:require [shared.node-cache.core :refer [get-node]]))


; HACK:
(def ?instance-frame-size [100 100])

(defonce pi js/Math.PI)
(defonce two-pi (* 2.0 pi))


(defn clear-canvas [ctx]
  (let [canvas (.-canvas ctx)
        width  (.-width canvas)
        height (.-height canvas)]
    (.clearRect ctx 0 0 width height)))

(defn circle [ctx x y r]
  (.arc ctx x y r 0.0 two-pi))

; TODO: Take into account icon size, margin, & padding
; TODO: Define multimethod for camera, instance, pin
; TODO: Define AABB constructor function
; TODO: Move definition to aabb ns
(defn aabb
  "The instance's axis-aligned bounding box in canvas-space."
  [instance]
  (let [{translation :translation
         scale       :scale      } (-> instance :view :transform)
        size  (mapv * ?instance-frame-size scale)
        start translation
        end   (mapv + translation size)]
    {:size  size
     :start start
     :end   end  }))

(defn space-instance->canvas
  "Converts a point from instance-space to canvas-space."
  [point instance]
  (let [instance-origin (-> instance
                            :view
                            :transform
                            :translation)]
    (mapv + point instance-origin)))

(defn space-canvas->instance
  "Converts a point from canvas-space to instance-space."
  [point instance]
  (let [instance-origin (-> instance
                            :view
                            :transform
                            :translation)]
    (mapv - point instance-origin)))


(defn- pin-y
  "Instance pin's y-position in instance-space."
  [i n h]
  (let [j (+ i 0.5)
        t (/ j n)
        y (* t h)]
    y))

(defn pin-positions
  "Instance pin positions in canvas-space."
  [node-cache instance]
  (let [{[w h] :size} (aabb instance)
        {input-types  :inputs
         output-types :outputs} (->> instance
                                     :full-path
                                     (get-node node-cache)
                                     :model
                                     :metadata)

        inputs  (map-indexed
                  (fn [i _]
                    (space-instance->canvas
                      [0 (pin-y i (count input-types) h)]
                      instance))
                  input-types)
        inputs  (vec inputs)

        outputs (map-indexed
                  (fn [i _]
                    (space-instance->canvas
                      [w (pin-y i (count output-types) h)]
                      instance))
                  output-types)
        outputs (vec outputs)]
    {:inputs  inputs
     :outputs outputs}))
