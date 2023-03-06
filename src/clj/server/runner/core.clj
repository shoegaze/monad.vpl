(ns server.runner.core
  (:require [clojure.core.async :refer [go]]
            [server.runner.executor :as executor]
            [server.runner.loader :as loader]
            [taoensso.timbre :as timbre]))


(defonce node-cache (atom nil))
(defonce node-graph (atom nil))


(defn start-runner [packages graphs]
  (timbre/info "> Generating node cache")
  (reset! node-cache {})

  (timbre/info "> Loading nodes from directory:" (.toString packages))
  (loader/load-nodes! node-cache packages)

  (timbre/info "> Generating node graph")
  (reset! node-graph {})

  (let [graph-uri     (.toURI graphs)
        default-graph (.resolve graph-uri "graphs/shoegaze/math.json")]
    (timbre/info "> Loading graphs from directory:" (.toString graphs))
    (loader/load-graph! node-graph default-graph))

  ; TODO: Create new executor thread
  (let [step-rate 1000/1]
    (timbre/info "Starting executor thread ...")
    (go (executor/execute-loop node-cache
                               node-graph
                               step-rate))))
