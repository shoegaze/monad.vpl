(ns client.controller.draw
  (:require [taoensso.timbre :as timbre]
            [shared.node-cache.core :refer [get-node]]))


(defonce PI js/Math.PI)
(defonce TWO_PI (* 2.0 PI))

(defn- clear [ctx]
  (set! (.-fillStyle ctx) "blue")
  (let [canvas (.-canvas ctx)
        width  (.-width canvas)
        height (.-height canvas)]
    (.clearRect ctx 0 0 width height)))

(defn- circle [ctx x y r]
  (.arc ctx x y r 0.0 TWO_PI))

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
      .beginPath
      (.translate dx dy)
      (.scale sx sy)
      (.fillText name (/ w 2) (/ h 2))
      (.setTransform 1 0 0
                     1 0 0)
      .closePath)))

(defn- pin-position [i n h]
  (let [j (+ i 0.5)
        t (/ j n)]
    (* t h)))

(defn- draw-instance-input-pin [ctx instance i inputs]
  (set! (.-fillStyle ctx) "aquamarine")
  (set! (.-strokeStyle ctx) "black")

  (let [{[dx dy] :translation
         [sx sy] :scale} (-> instance :view :transform)
        {[_ h] :size} (aabb instance)
        r 10.0]
    (doto ctx
      .beginPath
      (.translate dx dy)
      (.scale sx sy)
      (circle 0 (pin-position i (count inputs) h) r)
      .fill
      .stroke
      (.setTransform 1 0 0
                     1 0 0)
      .closePath)))

(defn- draw-instance-output-pin [ctx instance i outputs]
  (set! (.-fillStyle ctx) "orangered")
  (set! (.-strokeStyle ctx) "black")

  (let [{[dx dy] :translation
         [sx sy] :scale} (-> instance :view :transform)
        {[w h] :size} (aabb instance)
        r 10.0]
    (doto ctx
      .beginPath
      (.translate dx dy)
      (.scale sx sy)
      (circle w (pin-position i (count outputs) h) r)
      .fill
      .stroke
      (.setTransform 1 0 0
                     1 0 0)
      .closePath)))

(defn- draw-instance-pins [ctx instance node-cache]
  (let [{inputs  :inputs
         outputs :outputs} (->> instance
                                :full-path
                                (get-node node-cache)
                                :model
                                :metadata)]
    (let [n (count inputs)]
      (doseq [i (range n)]
        (draw-instance-input-pin ctx instance i inputs)))

    (let [n (count outputs)]
      (doseq [i (range n)]
        (draw-instance-output-pin ctx instance i outputs)))))

(defn- draw-instance [ctx instance node-cache]
  (draw-instance-frame ctx instance)
  (draw-instance-icon ctx instance node-cache)
  (draw-instance-pins ctx instance node-cache))

(defn- draw-instances [ctx node-cache node-graph]
  (let [instances (:instances node-graph)]
    (doseq [instance instances]
      (draw-instance ctx instance node-cache))))


(defn- draw-edge [ctx edge]
  :TODO)

(defn- draw-edges [ctx edges]
  (let [edges (:edges edges)]
    (doseq [edge edges]
      (draw-edge ctx edge))))


(defn draw [node-cache node-graph ctx]
  (timbre/debug "Rendering canvas")

  (clear ctx)

  ; TODO: Rename atoms with trailing _
  (let [node-cache @node-cache
        node-graph @node-graph]
    (draw-instances ctx node-cache node-graph)
    ;(draw-edges     ctx node-graph)
    ))
