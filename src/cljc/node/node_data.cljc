(ns node.node-data)


(defprotocol INodeData
  (id [this]))

(defrecord NodeData [full-path meta script icons]
  INodeData

  (id [this]
    (hash (:full-path this))))
