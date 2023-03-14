(ns graph.node-graph
  (:require [node.node-instance :refer [instance-id]]))


(defprotocol INodeGraph
  (add-node [this node])
  (remove-node [this node]))

(defrecord NodeGraph [nodes edges]
  INodeGraph

  (add-node [_ node]
    (let [new-nodes (assoc nodes (instance-id node) node)]
      (->NodeGraph new-nodes edges)))

  (remove-node [_ node]
    (let [new-nodes (dissoc nodes (instance-id node) node)]
      (->NodeGraph new-nodes edges))))
