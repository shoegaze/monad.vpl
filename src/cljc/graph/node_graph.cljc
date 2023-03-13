(ns graph.node-graph)


(defprotocol INodeGraph
  )

(defrecord NodeGraph [nodes edges]
  INodeGraph

  )
