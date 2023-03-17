(ns shared.node-cache.node-view)


; TODO: Unify INodeView with INodeModel
(defprotocol INodeView
  (get-node-id [this]))

(defrecord NodeView [full-path transform style icons]
  INodeView

  (get-node-id [_]
    (hash full-path)))
