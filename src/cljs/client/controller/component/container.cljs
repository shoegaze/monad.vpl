(ns client.controller.component.container
  (:require [reagent.core :as r]
            [ajax.core :as ajax]
            [client.controller.component.viewport :refer [viewport]]
            [client.controller.component.menubar :refer [menubar]]
            [client.controller.component.sidebar :refer [sidebar]]))


(defn fetch-node-cache! [node-cache]
  (let [on-success (fn [json]
                     (js/console.log "Loading node cache")
                     (reset! node-cache json))
        on-error   (fn []
                     (js/console.error "Could not load the node cache!"))]
    (ajax/GET "api/node" {:response-format :json
                          :keywords? true
                          :handler on-success
                          :error-handler on-error})))

(defn fetch-node-graph! [node-graph]
  (let [on-success (fn [json]
                     (js/console.log "Loading node graph")
                     (reset! node-graph json))
        on-error   (fn []
                     (js/console.error "Could not load the node graph!"))]
    (ajax/GET "api/graph" {:response-format :json
                           :keywords? true
                           :handler on-success
                           :error-handler on-error})))

(defn container []
  (let [node-cache (r/atom nil)
        node-graph (r/atom nil)]
    (fetch-node-cache! node-cache)
    (fetch-node-graph! node-graph)

    (fn []
      [:div.container
       [viewport]
       [menubar]
       [sidebar node-cache]])))
