(ns client.controller.component.viewport
  (:require [reagent.core :as r]
            [client.controller.draw.core :refer [draw]]))


(defn viewport [_node-cache _node-graph]
  (let [canvas (r/atom nil)]
    (fn [node-cache node-graph]
      (when-let [c @canvas]
        (let [ctx (.getContext c "2d")]
          (draw node-cache node-graph ctx)))

      [:canvas.viewport
       {:ref    #(reset! canvas %)
        :width  js/innerWidth
        :height js/innerHeight}])))
