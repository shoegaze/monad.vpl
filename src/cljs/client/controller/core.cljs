(ns client.controller.core
  (:require [reagent.dom :as rdom]
            [domina :refer (by-id)]
            [client.controller.component.container :refer (container)]))


(defn ^:export init []
  (rdom/render [container]
               (by-id "root")))