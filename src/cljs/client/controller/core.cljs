(ns client.controller.core
  (:require [taoensso.timbre :as timbre]
            [reagent.dom :as rdom]
            [client.controller.component.container :refer [container]]
            [client.controller.http.socket :as ws]))


(def csrf-token
  (-> "__anti-forgery-token"
      js/document.getElementById
      .-value))

(defn ^:export init []
  (timbre/set-min-level! :debug)

  (ws/start-router csrf-token)
  (rdom/render
    [container]
    (js/document.getElementById "root")))
