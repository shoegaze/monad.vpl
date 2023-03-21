(ns shared.node-cache.core
  (:require [shared.node-cache.node-model :as model]
            [shared.node-cache.node-view :as view]))


(defprotocol INodeCache
  (get-node  [this full-path])
  (add-model [this node-model])
  (add-view  [this node-view]))

; :cache { node-id { :model ^NodeModel
;                    :view  ^NodeView  }}
(defrecord NodeCache [cache]
  INodeCache

  (get-node [_ full-path]
    ; TODO: Unify get-node-id methods
    (let [node-id (hash full-path)]
      (get cache node-id)))

  (add-model [_ node-model]
    (let [node-id   (model/node-id node-model)
          new-cache (assoc-in cache [node-id :model] node-model)]
      (->NodeCache new-cache)))

  (add-view [_ node-view]
    (let [node-id   (view/node-id node-view)
          new-cache (assoc-in cache [node-id :view] node-view)]
      (->NodeCache new-cache))))
