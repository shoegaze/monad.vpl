(ns client.controller.component.interface
  (:require [client.controller.component.viewport :refer (viewport)]
            [client.controller.component.menubar :refer (menubar)]
            [client.controller.component.sidebar :refer (sidebar)]))


(defn interface []
  [:div.interface
   [viewport]
   [menubar]
   [sidebar]])