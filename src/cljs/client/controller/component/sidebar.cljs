(ns client.controller.component.sidebar
  (:require [reagent.core :as r]))


(defonce selected-node (r/atom nil))

(defn sidebar []
  [:div.sidebar
   [:h1.sidebar-title "Palette"]
   [:div.sidebar-container
    (for [i (range 20)
          :let [name (str "Node:" i)]]
      ^{:key i} [:input.sidebar-entry {:type "button"
                                       :value name
                                       :on-click #(reset! selected-node {:id i
                                                                         :name name})}])]
  (when-let [name (:name @selected-node)]
   [:div.sidebar-info
    [:h1.sidebar-info-title name]
    [:h2.sidebar-info-metadata "{{Metadata}}"]
    [:div.sidebar-info-description "{{Description}}"]])])