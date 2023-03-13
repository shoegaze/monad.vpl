(ns graph.node-graph)


(defprotocol INodeGraph
  (add-node [this node])
  ;(add-edge [this edge])
  )

(defrecord NodeGraph [nodes edges]
  INodeGraph

  (add-node [_ node]
    (->NodeGraph (conj nodes node) edges)))
