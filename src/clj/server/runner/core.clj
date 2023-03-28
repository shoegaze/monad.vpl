(ns server.runner.core
  (:require [clojure.core.async :refer [go]]
            [taoensso.timbre :as timbre]
            [shared.node-cache.core :refer [make-node-cache]]
            [shared.graph.core :refer [make-graph]]
            [server.runner.executor :as executor]
            [server.runner.loader :as loader]
            [server.state :refer [node-cache_ node-graph_]]))


(defn start-runner [packages-dir graphs-dir]
  (timbre/info "> Generating node cache")
  (reset! node-cache_ (make-node-cache))

  (timbre/info "> Loading nodes from directory:" (.toString packages-dir))
  (loader/load-nodes! node-cache_ packages-dir)

  (timbre/debug "> Node cache:" @node-cache_)


  (timbre/info "> Generating node graph")
  (reset! node-graph_ (make-graph))

  (let [graph-uri     (.toURI graphs-dir)
        default-graph (.resolve graph-uri "graphs/shoegaze/math.edn")]
    (timbre/info "> Loading graphs from directory:" (.toString graphs-dir))
    (loader/load-graph! node-graph_ default-graph))

  (timbre/debug "> Node graph:" @node-graph_)


  ; TODO: Create new executor thread
  (let [step-rate 1000/1]
    (timbre/info "Starting executor thread")
    (go (executor/execute-loop node-cache_
                               node-graph_
                               step-rate))))
