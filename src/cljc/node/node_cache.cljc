(ns node.node-cache
  (:require [node.node-instance :refer [id]]))


(defprotocol INodeCache
  (add-entry [this node]))

(defrecord NodeCache [cache]
  INodeCache

  (add-entry [_ node]
    (->NodeCache
      (assoc cache (id node) node))))
