(ns node.node-instance)


(defprotocol INodeInstance
  (id [this]))

(defrecord NodeInstance [model view]
  INodeInstance

  (id [_]
    (hash (:full-path model))))
