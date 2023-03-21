(ns shared.node-cache.node-model)


;; TODO:
(defn valid-node-model? [_node-model]
  true)

(defn make-node-model [full-path metadata script]
  (let [node-model {:full-path full-path
                    :metadata  metadata
                    :script    script}]
    ; TODO: Throw error when not valid?
    (when (valid-node-model? node-model)
      node-model)))
