(ns server.runner.core
  (:require [clojure.core.async :refer (go)]
            [taoensso.timbre :as timbre]
            [server.runner.loader :as loader]
            [server.runner.executor :as executor]))


(defonce node-cache (atom nil))
(defonce node-graph (atom nil))


(defn start-runner [packages]
  (timbre/info "> Generating node cache")
  (reset! node-cache {})

  (timbre/info "> Loading nodes from directory:" (.toString packages))
  (loader/load-nodes! node-cache packages)

  (timbre/info "> Generating node graph")
  (reset! node-graph {})
  ;(loader/load-graph! node-graph graph-path)

  ; TODO: Create new executor thread
  (let [step-rate 1000/1]
    (go (executor/execute-loop node-cache
                               node-graph
                               step-rate))))
