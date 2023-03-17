(ns server.http.router
  (:require [clojure.data.json :as json]
            [taoensso.timbre :as timbre]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [hiccup.page :refer [html5 include-css include-js]]
            [ring.util.anti-forgery :refer [anti-forgery-field]]
            [ring.middleware.defaults :refer :all]
            [ring.middleware.cookies :refer :all]
            [ring.middleware.anti-forgery :refer :all]
            [shared.graph.core :refer [add-instance remove-instance]]
            [server.state :refer [node-cache node-graph]]))


(defn- html-index []
  (html5
    [:head
     [:title "vpl.dev"]
     (include-css "css/index.css")]
    [:body
     [:div#root]
     (anti-forgery-field)

     (include-js "js/bundle/bundle.dev.js")
     [:script "client.controller.core.init()"]
     [:noscript "Please enable javascript in your browser to use the controller!"]]))

(defn- api-get-node-cache []
  (timbre/info "GET: /api/nodes")
  (let [json     @node-cache
        json-str (json/write-str json)]
    {:status 200
     :headers {"Content-Type" "text/json"}
     :body json-str}))

(defn- api-get-node-graph []
  (timbre/info "GET: /api/graph")
  (let [json     @node-graph
        json-str (json/write-str json)]
    {:status 200
     :headers {"Content-Type" "text/json"}
     :body json-str}))

(defn- api-post-add-graph-node! [node]
  (timbre/info "POST: /api/graph/node/add" @node-graph)

  (swap! node-graph add-instance node)

  (let [json     @node-graph
        json-str (json/write-str json)]
    {:status 200
     :headers {"Content-Type" "text/json"}
     :body json-str}))

(defn- api-post-remove-graph-node! [node]
  (timbre/info "POST: /api/graph/node/remove" @node-graph)

  (swap! node-graph remove-instance node)

  (let [json     @node-graph
        json-str (json/write-str json)]
    {:status 200
     :headers {"Content-Type" "text/json"}
     :body json-str}))

; TODO: Add logs
(defroutes app
           (GET "/" [] (html-index))
           (GET "/api/nodes" [] (api-get-node-cache))
           (GET "/api/graph" [] (api-get-node-graph))
           (POST "/api/graph/nodes/add"    [instance] (api-post-add-graph-node! instance))
           (POST "/api/graph/nodes/remove" [instance] (api-post-remove-graph-node! instance))
           ;(POST "/api/graph/edges/add" [edge] (TODO))
           ;(POST "/api/graph/edges/remove" [edge] (TODO))
           (route/not-found "404"))

(defonce site
         (-> app
             (wrap-defaults site-defaults)))