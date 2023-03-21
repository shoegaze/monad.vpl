(ns shared.node-cache.core)


;; TODO:
(defn valid-node-cache? [_node-cache]
  true)

; :cache { node-id { :model ^NodeModel
;                    :view  ^NodeView  }}
(defn make-node-cache
  ([] (make-node-cache {}))
  ([cache]
   (let [node-cache {:cache cache}]
     ; TODO: Throw error when not valid?
     (when (valid-node-cache? node-cache)
       node-cache))))

(defn get-node [node-cache full-path]
  (let [cache   (:cache node-cache)
        node-id (hash full-path)]
    (get cache node-id)))

(defn add-model [node-cache node-model]
  (let [full-path (:full-path node-model)
        old-cache (:cache node-cache)
        new-cache (assoc-in old-cache [full-path :model] node-model)]
    (make-node-cache new-cache)))

(defn add-view [node-cache node-view]
  (let [full-path (:full-path node-view)
        old-cache (:cache node-cache)
        new-cache (assoc-in old-cache [full-path :view] node-view)]
    (make-node-cache new-cache)))
