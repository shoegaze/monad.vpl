(ns node.node-instance)


(defprotocol INodeInstance
  )

(defrecord NodeInstance [model view]
  INodeInstance

  )
