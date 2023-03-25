(ns client.controller.draw.core
  (:require [taoensso.timbre :as timbre]
            [client.controller.draw.util :as util]
            [client.controller.draw.camera :as cam]
            [client.controller.draw.instance :refer [draw-instances]]
            [client.controller.draw.edge :refer [draw-edges]]))


(defn draw [ctx camera_ node-cache_ node-graph_]
  (timbre/debug "Rendering canvas")

  (util/clear-canvas ctx)
  (cam/apply-transform ctx @camera_)

  (let [node-cache @node-cache_
        node-graph @node-graph_]
    (draw-instances ctx node-cache node-graph)
    (draw-edges     ctx node-cache node-graph))

  (.resetTransform ctx))
