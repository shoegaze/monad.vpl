(ns shared.graph.graph-edge)


;; TODO:
(defn valid-graph-edge? [_node-graph _graph-edge]
  true)

(defn make-graph-edge [out-instance-id out-pin
                       in-instance-id  in-pin]
  (let [graph-edge {:out-instance-id out-instance-id
                    :out-pin         out-pin
                    :in-instance-id  in-instance-id
                    :in-pin          in-pin}]
    ; TODO: Throw error when not valid?
    (when (valid-graph-edge? :TODO graph-edge)
      graph-edge)))

;(defn edge-id [graph-edge]
;  (hash graph-edge))
