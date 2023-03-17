(ns server.runner.core
  (:require [clojure.core.async :refer [go]]
            [taoensso.timbre :as timbre]
            [shared.node-cache.core :refer [->NodeCache]]
            [shared.graph.core :refer [->Graph]]
            [server.runner.executor :as executor]
            [server.runner.loader :as loader]
            [server.state :refer [node-cache node-graph]]))


(defn start-runner [packages-dir graphs-dir]
  (timbre/info "> Generating node cache")
  (reset! node-cache (->NodeCache {}))

  (timbre/info "> Loading nodes from directory:" (.toString packages-dir))
  (loader/load-nodes! node-cache packages-dir)

  (timbre/info "> Generating node graph")
  (reset! node-graph (->Graph {} []))

  (let [graph-uri     (.toURI graphs-dir)
        default-graph (.resolve graph-uri "graphs/shoegaze/math.json")]
    (timbre/info "> Loading graphs from directory:" (.toString graphs-dir))
    (loader/load-graph! node-cache node-graph default-graph))

  ; TODO: Create new executor thread
  (let [step-rate 1000/1]
    (timbre/info "Starting executor thread")
    (go (executor/execute-loop node-cache
                               node-graph
                               step-rate))))
