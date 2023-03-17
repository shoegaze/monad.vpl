(ns shared.graph.core)


(defprotocol IGraph
  (add-instance    [this instance])
  (remove-instance [this instance]))

; :instances { instance-id ^GraphInstance }
; :edges     { edge-id     ^GraphEdge     }
(defrecord Graph [instances edges]
  IGraph

  (add-instance [_ instance]
    (let [id        (:instance-id instance)
          new-nodes (assoc instances id instance)]
      (->Graph new-nodes edges)))

  (remove-instance [_ instance]
    (let [id        (:instance-id instance)
          new-nodes (dissoc instances id instance)]
      (->Graph new-nodes edges))))
