(ns shared.graph.graph-edge)


(defprotocol IGraphEdge
  ;(valid? [this node-cache])
  (edge-id [this])
  )

(defrecord GraphEdge [out-instance out-pin
                      in-instance  in-pin]
  IGraphEdge

  (edge-id [this]
    (hash this)))
