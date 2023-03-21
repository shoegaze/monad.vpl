(ns server.core
  (:require [clojure.java.io :refer [resource]]
            [taoensso.timbre :as timbre]
            [server.runner.core :as runner]
            [server.http.server :as server]))


; TODO: Arguments:
;   * :port port                  ... Sets the server port
;   * :headless flag              ... Starts the server in headless mode (No render output)
;   * :output-to dir              ... Executor copies output to given directory
;   * :resolution [width height]  ... Sets the output resolution
;   * :tick-rate                  ... Sets the simulation tick rate
(defn -main [& _args]
  (timbre/set-min-level! :debug)

  ; Language runner
  (timbre/info "Starting language runner")
  (runner/start-runner (resource "server/packages")
                       (resource "server/graphs"))

  ; Head
  ;(timbre/info "Starting head")
  ;(head/start-head)

  ; HTTP/WebSocket server
  ;  Needs to be placed last because these block the thread
  (server/start-server "localhost" 8886))