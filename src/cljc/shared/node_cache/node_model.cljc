(ns shared.node-cache.node-model)


; TODO: Unify INodeModel with INodeView
(defprotocol INodeModel
  (get-node-id [this]))

(defrecord NodeModel [full-path meta script]
  INodeModel

  (get-node-id [_]
    (hash full-path)))
