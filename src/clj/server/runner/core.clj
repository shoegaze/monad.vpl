(ns server.runner.core
  (:require
            [taoensso.timbre :as timbre]
            [server.runner.loader :as loader]))


(defonce node-cache (atom nil))
;(defonce node-graph (atom nil))


(defn start-runner [packages-path]
  (timbre/info "> Generating node cache")
  (reset! node-cache {})

  (timbre/info "> Loading nodes from path:" packages-path)
  (loader/load-nodes! node-cache packages-path)

  ;(timbre/info "> Generating node graph")
  ;(reset! node-graph (new-node-graph))
  )
