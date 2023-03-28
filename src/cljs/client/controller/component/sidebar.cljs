(ns client.controller.component.sidebar
  (:require [reagent.core :as r]))


(defn sidebar [_node-cache_]
  (let [selected-node_ (r/atom nil)]
    (fn [node-cache_]
      [:div.sidebar
       [:h1.sidebar-title "Palette"]
       [:div.sidebar-container
        (when-some [cache (:cache @node-cache_)]
          (for [[id node] cache
                :let [full-path (-> node :model :full-path)
                      on-click  (fn []
                                  (reset! selected-node_ node))]]
            ^{:key id} [:input.sidebar-entry {:type "button"
                                              :value full-path
                                              :on-click on-click}]))]
       (when-some [node @selected-node_]
         (let [node-model (:model node)
               {full-path :full-path
                {:keys [tags description]} :metadata} node-model]
           [:div.sidebar-info
            [:h1.sidebar-info-title full-path]
            [:h2.sidebar-info-tags
             (map-indexed
               (fn [i tag]
                 ^{:key i} [:span.sidebar-info-tag tag])
               tags)]
            [:div.sidebar-info-description description]]))])))
