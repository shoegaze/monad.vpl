(ns node.node-view)


(defprotocol INodeView)

(defrecord NodeView [transform style icons]
  INodeView)
