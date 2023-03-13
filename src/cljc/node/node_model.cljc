(ns node.node-model)


(defprotocol INodeModel
  (id [this]))

(defrecord NodeModel [full-path meta script icons]
  INodeModel

  (id [this]
    (hash (:full-path this))))
