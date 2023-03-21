(ns client.controller.component.viewport)


(defn viewport [_node-cache _node-graph]
  [:canvas.viewport {:width js/innerWidth
                     :height js/innerHeight}])