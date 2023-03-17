(ns client.controller.component.container
  (:require [reagent.core :as r]
            [ajax.core :as ajax]
            [shared.graph.graph-instance :refer [->GraphInstance]]
            [client.controller.component.viewport :refer [viewport]]
            [client.controller.component.menubar :refer [menubar]]
            [client.controller.component.sidebar :refer [sidebar]]))


(defn- get-csrf-token []
  (-> "__anti-forgery-token"
      (js/document.getElementById)
      (.-value)))

(defn- fetch-node-cache! [node-cache]
  (let [on-success (fn [json]
                     (js/console.log "Loading node cache")
                     (reset! node-cache json))
        on-error   (fn []
                     (js/console.error "Could not load the node cache!"))]
    (ajax/GET "/api/nodes" {:response-format :json
                            :keywords? true
                            :handler on-success
                            :error-handler on-error})))

(defn fetch-node-graph! [node-graph]
  (let [on-success (fn [json]
                     (js/console.log "Loading node graph")
                     (reset! node-graph json))
        on-error   (fn []
                     (js/console.error "Could not load the node graph!"))]
    (ajax/GET "/api/graph" {:response-format :json
                            :keywords? true
                            :handler on-success
                            :error-handler on-error})))

(defn- post-add-graph-instance! [instance]
  (ajax/POST "/api/graph/nodes/add" {:params {:instance instance}
                                     :headers {:X-CSRF-Token (get-csrf-token)}
                                     :keywords? true
                                     :handler #(js/console.log "instance added:" %)
                                     :error-handler #(js/console.error "instance not added")}))

(defn- post-remove-graph-instance! [instance]
  (ajax/POST "/api/graph/nodes/remove" {:params {:instance instance}
                                        :headers {:X-CSRF-Token (get-csrf-token)}
                                        :keywords? true
                                        :handler #(js/console.log "instance removed:" %)
                                        :error-handler #(js/console.error "instance not removed")}))

(defn container []
  (let [node-cache (r/atom nil)
        node-graph (r/atom nil)]
    (fetch-node-cache! node-cache)
    (fetch-node-graph! node-graph)

    ; DEBUG:
    (post-add-graph-instance!
      (->GraphInstance 4 {} {}))
    ;(post-remove-graph-node! 0)

    (fn []
      [:div.container
       [viewport]
       [menubar]
       [sidebar node-cache]])))
