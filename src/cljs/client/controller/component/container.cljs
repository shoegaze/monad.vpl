(ns client.controller.component.container
  (:require [client.controller.component.viewport :refer [viewport]]
            [client.controller.component.menubar :refer [menubar]]
            [client.controller.component.sidebar :refer [sidebar]]))


(defn container []
  [:div.container
   [viewport]
   [menubar]
   [sidebar]])