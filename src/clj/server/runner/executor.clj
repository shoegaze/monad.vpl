(ns server.runner.executor)


(defn dirty? [_node-cache _node-graph]
  true)

; TODO: Pass step-context map
(defn step [_node-cache _node-graph]
  (taoensso.timbre/info "Stepping..."))

; TODO: Pass execute-context map
; TODO: Return error code
; TODO: Start only when :start message is received
(defn execute-loop [node-cache node-graph step-rate]
  (while true
    (when (dirty? node-cache node-graph)
      (step node-cache node-graph))
    ; TODO: Try to sync with (n * step-rate)
    (Thread/sleep step-rate))
  true)
