(ns client.controller.http.action
  (:require [taoensso.timbre :as timbre]
            [taoensso.sente :refer [cb-success?]]
            [client.controller.http.socket :as ws]))


(defn fetch-node-cache! [node-cache]
  (timbre/info "Fetching node cache")
  (ws/chsk-send! [:vpl/get-nodes] 1000
    (fn [reply]
      (if (cb-success? reply)
        (do
          (timbre/info "> Fetched node cache:" reply)
          (reset! node-cache reply))
        (timbre/warn "> Could not fetch node cache:" reply)))))

(defn fetch-node-graph! [node-graph]
  (timbre/info "Fetching node graph")
  (ws/chsk-send! [:vpl/get-graph] 1000
    (fn [reply]
      (if (cb-success? reply)
        (do
          (timbre/info "> Fetched node graph:" reply)
          (reset! node-graph reply))
        (timbre/warn "> Could not fetch node graph:" reply)))))
