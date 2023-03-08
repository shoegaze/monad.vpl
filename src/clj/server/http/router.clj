(ns server.http.router
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.util.response :refer [content-type resource-response]]
            [ring.middleware.defaults :refer :all]
            [cheshire.core :as json]
            [server.state :refer [node-cache node-graph]]))


; TODO:
;  Site mode: :dev | :pre | :prod
;  Switch between "index.{mode}.html"
(defn- html-index []
  (content-type
    (resource-response "index.dev.html" {:root "public"})
    "text/html"))

(defn- api-get-node-cache []
  {:status 200
   :headers {"Content-Type" "text/json"}
   :body (json/generate-string @node-cache)})

(defn- api-get-node-graph []
  {:status 200
   :headers {"Content-Type" "text/json"}
   :body (json/generate-string @node-graph)})

(defroutes app
           (GET "/" [] (html-index))
           (GET "/api/node" [] (api-get-node-cache))
           (GET "/api/graph" [] (api-get-node-graph))
           (route/not-found "404"))

(defonce site
         (wrap-defaults app site-defaults))