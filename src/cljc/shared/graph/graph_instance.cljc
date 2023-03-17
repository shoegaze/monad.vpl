(ns shared.graph.graph-instance)


(defprotocol IGraphInstance)

(defrecord GraphInstance [instance-id model view]
  IGraphInstance)
