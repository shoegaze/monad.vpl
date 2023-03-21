(ns shared.graph.graph-edge)


;; TODO:
(defn valid-graph-edge? [_node-graph _graph-edge]
  true)

(defn make-graph-edge [out-instance out-pin
                       in-instance  in-pin]
  (let [graph-edge {:out-instance out-instance
                    :out-pin      out-pin
                    :in-instance  in-instance
                    :in-pin       in-pin}]
    ; TODO: Throw error when not valid?
    (when (valid-graph-edge? :TODO graph-edge)
      graph-edge)))

;(defn edge-id [graph-edge]
;  (hash graph-edge))
