(ns client.controller.core
  (:require [reagent.dom :as rdom]
            [client.controller.component.container :refer [container]]))


(defn ^:export init []
  (rdom/render [container]
               (js/document.getElementById "root")))
