(ns server.http.router
  (:require [ring.util.anti-forgery :refer [anti-forgery-field]]
            [ring.middleware.defaults :refer :all]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [hiccup.page :refer [html5 include-css include-js]]
            [server.http.socket :refer [ring-ajax-get-or-ws-handshake ring-ajax-post]]))


(defn- html-index [_req]
  (html5
    [:head
     [:title "vpl.dev"]
     (include-css "css/index.css")]
    [:body
     (anti-forgery-field)
     [:div#root]

     (include-js "js/bundle/bundle.dev.js")
     [:script "client.controller.core.init()"]
     [:noscript "Please enable javascript in your browser to use the controller!"]]))

; TODO: Add logs
(defroutes app
  (GET  "/"     req (html-index req))
  (GET  "/chsk" req (ring-ajax-get-or-ws-handshake req))
  (POST "/chsk" req (ring-ajax-post                req))
  (route/not-found "404"))

(defonce site
  (-> app
      (wrap-defaults site-defaults)))
