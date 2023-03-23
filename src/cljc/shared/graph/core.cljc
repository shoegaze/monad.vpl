(ns shared.graph.core)


;; TODO:
(defn valid-graph? [_graph]
  true)

; :instances {instance-id ^GraphInstance}
; :edges     [^GraphEdge]
(defn make-graph
  ([] (make-graph {} []))
  ([instances edges]
   (let [graph {:instances instances
                :edges     edges}]
     ; TODO: Throw error when not valid?
     (when (valid-graph? graph)
       graph))))

; WARN: Does not preserve original order
(defn get-instances [graph]
  (->> graph
       :instances
       vals
       (sort-by :instance-id)))

(defn get-instance [graph instance-id]
  ; TODO: Use get-instances ?
  (let [instances (:instances graph)]
    (get instances instance-id)))

;(defn update-instance [graph instance]
;  (let [{instances :instances
;         edges     :edges} graph
;        id        (:instance-id instance)
;        new-nodes (assoc instances id instance)]
;    (make-graph new-nodes edges)))

;(defn remove-instance [graph instance]
;  (let [{instances :instances
;         edges     :edges} graph
;        id        (:instance-id instance)
;        new-nodes (dissoc instances id instance)]
;      (make-graph new-nodes edges)))
