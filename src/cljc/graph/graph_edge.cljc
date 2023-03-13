(ns graph.graph-edge)


(defprotocol IGraphEdge
  ;(valid? [this node-cache])
  )

(defrecord GraphEdge [out-node in-node, out-pin in-pin]
  IGraphEdge

  )
