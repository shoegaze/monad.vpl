(ns node.node-data)


(defprotocol INodeData
  (get-id [this]))

(defrecord NodeData [full-path meta script icons]
  INodeData

  (get-id [this]
    (hash (:full-path this))))
