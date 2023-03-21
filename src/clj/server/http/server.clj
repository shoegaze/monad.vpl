(ns server.http.server
  (:require [taoensso.timbre :as timbre]
            [org.httpkit.server :as http-kit]
            [server.http.socket :as ws]
            [server.http.router :as router]
            [server.http.handler :refer [event-msg-handler]]))


(defn start-server [host port]
  (let [url  (str "http://" host ":" port)]
    (ws/start-router event-msg-handler)

    (timbre/info "Starting HTTP server at" url)
    (http-kit/run-server router/site {:host host
                                      :port  port})))
