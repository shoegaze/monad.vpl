(ns node.node-model)


(defprotocol INodeModel)

(defrecord NodeModel [full-path meta script]
  INodeModel)
