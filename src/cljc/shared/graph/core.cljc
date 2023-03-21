(ns shared.graph.core)


(defn valid-graph?
  "TODO"
  [_graph]
  true)

(defn make-graph [instances edges]
  (let [graph {:instances instances
               :edges     edges}]
    ; TODO: Throw error when not valid?
    (when (valid-graph? graph)
      graph)))

(defn update-instance [graph instance]
  (let [{instances :instances
         edges     :edges} graph
        id        (:instance-id instance)
        new-nodes (assoc instances id instance)]
    (make-graph new-nodes edges)))

(defn remove-instance [graph instance]
  (let [{instances :instances
         edges     :edges} graph
        id        (:instance-id instance)
        new-nodes (dissoc instances id instance)]
      (make-graph new-nodes edges)))
