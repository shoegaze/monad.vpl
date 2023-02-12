(ns client.controller.core
  (:require [reagent.dom :as rdom]
            [domina :refer (by-id)]
            [client.controller.component.interface :refer (interface)]))


; TODO
(defn ^:export init []
  (js/console.log "Hello, controller!")
  (rdom/render [interface]
               (by-id "container")))