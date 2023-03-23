(ns client.controller.draw.core
  (:require [taoensso.timbre :as timbre]
            [client.controller.draw.util :refer [clear-canvas]]
            [client.controller.draw.instance :refer [draw-instances]]
            [client.controller.draw.edge :refer [draw-edges]]))


(defn draw [node-cache_ node-graph_ ctx]
  (timbre/debug "Rendering canvas")

  (clear-canvas ctx)

  (let [node-cache @node-cache_
        node-graph @node-graph_]
    (draw-instances ctx node-cache node-graph)
    (draw-edges     ctx node-cache node-graph)))
