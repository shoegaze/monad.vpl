(ns node.node-cache
  (:require [node.node-data :as nd]))


(defprotocol INodeCache
  (add-entry [this node-data]))

(defrecord NodeCache [cache]
  INodeCache

  (add-entry [_ node-data]
    (->NodeCache
      (assoc cache (nd/id node-data) node-data))))
