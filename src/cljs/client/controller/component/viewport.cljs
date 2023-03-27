(ns client.controller.component.viewport
  (:require [reagent.core :as r]
            [client.controller.draw.camera :as cam :refer [make-camera]]
            [client.controller.draw.core :refer [draw]]))


(defn viewport [_node-cache_ _node-graph_]
  (let [canvas_ (r/atom nil)
        camera_ (r/atom nil)
        camera-control?_   (r/atom false)
        mouse-down-origin_ (r/atom [0 0 0])]
    (fn [node-cache_ node-graph_]
      (when-some [c @canvas_]
        (when-not @camera_
          (let [translation [0 0 1]                         ; TODO: Calculate z where instance size ~ raw-size
                width       (.-width c)
                height      (.-height c)
                aspect      (/ width height)
                near        0.01
                far         1000.0]
            (reset! camera_ (make-camera translation
                                         aspect
                                         near
                                         far))))

        (let [ctx (.getContext c "2d")]
          (draw ctx camera_ node-cache_ node-graph_)))

      [:canvas.viewport
       {:ref     #(reset! canvas_ %)
        ; TODO: Update width, height on resize
        :width   js/innerWidth
        :height  js/innerHeight

        :on-mouse-down (fn [ev]
                         (let [x (.-clientX ev)
                               y (.-clientY ev)]
                           (reset! camera-control?_ true)
                           (reset! mouse-down-origin_ [x y 0])
                           ))
        :on-mouse-up    #(reset! camera-control?_ false)
        :on-mouse-move (fn [ev]
                         (when @camera-control?_
                           (let [x (.-clientX ev)
                                 y (.-clientY ev)
                                 displacement (mapv - [x y 0] @mouse-down-origin_)]
                             (swap! camera_ #(cam/translate % displacement)))))}])))
