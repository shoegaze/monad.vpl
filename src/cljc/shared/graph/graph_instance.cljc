(ns shared.graph.graph-instance)


;; TODO:
(defn valid-graph-instance? [_graph-instance]
  true)

(defn make-graph-instance [instance-id model view]
  (let [graph-instance {:instance-id instance-id
                        :model       model
                        :view        view}]
    ; TODO: Throw error when not valid?
    (when (valid-graph-instance? graph-instance)
      graph-instance)))
