(ns shared.node-cache.core
  (:require [shared.node-cache.node-model :as model]
            [shared.node-cache.node-view :as view]))


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
  (let [node-id   (model/node-id node-model)
        old-cache (:cache node-cache)
        new-cache (assoc-in old-cache [node-id :model] node-model)]
    (make-node-cache new-cache)))

(defn add-view [node-cache node-view]
  (let [node-id   (view/node-id node-view)
        old-cache (:cache node-cache)
        new-cache (assoc-in old-cache [node-id :view] node-view)]
    (make-node-cache new-cache)))
