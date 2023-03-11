(ns client.controller.component.container
  (:require [reagent.core :as r]
            [ajax.core :as ajax]
            [client.controller.component.viewport :refer [viewport]]
            [client.controller.component.menubar :refer [menubar]]
            [client.controller.component.sidebar :refer [sidebar]]))


(defn fetch-node-cache! [node-cache]
  (let [handler       (fn [json]
                        (reset! node-cache json))
        error-handler (fn []
                        (js/console.error "Could not load node-cache!"))]
    (ajax/GET "api/node" {:response-format :json
                          :keywords? true
                          :handler handler
                          :error-handler error-handler})))

(defn container []
  (let [node-cache (r/atom nil)]
    (fetch-node-cache! node-cache)
    (fn []
      [:div.container
       [viewport]
       [menubar]
       [sidebar node-cache]])))
