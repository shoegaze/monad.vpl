(ns client.controller.component.container
  (:require [reagent.core :as r]
            [client.controller.component.viewport :refer [viewport]]
            [client.controller.component.menubar :refer [menubar]]
            [client.controller.component.sidebar :refer [sidebar]]
            [client.controller.http.action :as action]
            [client.controller.state :as state]))


(defn container []
  (let [node-cache (r/atom nil)
        node-graph (r/atom nil)]

    ; HACK:
    ; TODO: Retry fetch on error
    ; TODO: Check if socket is open first
    (add-watch
      state/socket-open? :init
      (fn [_ _ _ new-state]
        (when new-state
          (action/fetch-node-cache! node-cache)
          (action/fetch-node-graph! node-graph))))

    (fn []
      [:div.container
       [viewport node-cache node-graph]
       [menubar]
       [sidebar node-cache]])))
