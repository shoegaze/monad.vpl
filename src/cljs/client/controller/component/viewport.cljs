(ns client.controller.component.viewport)


(defn viewport []
  [:canvas.viewport {:width js/innerWidth
                     :height js/innerHeight}])