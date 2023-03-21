(ns shared.node-cache.node-view)


;; TODO:
(defn valid-node-view? [_node-view]
  true)

(defn make-node-view [full-path transform style icons]
  (let [node-view {:full-path full-path
                   :transform transform
                   :style     style
                   :icons     icons}]
    ; TODO: Throw error when not valid?
    (when (valid-node-view? node-view)
      node-view)))
