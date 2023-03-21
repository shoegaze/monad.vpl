(ns server.http.socket
  (:require [taoensso.timbre :as timbre]
            [taoensso.sente :as sente]
            [taoensso.sente.server-adapters.http-kit :refer [get-sch-adapter]]))


(declare chsk-server
         ring-ajax-post
         ring-ajax-get-or-ws-handshake
         ch-chsk
         chsk-send!
         router)

(defn start-router [event-msg-handler]
  (timbre/info "Starting WebSocket router")

  (let [packer :edn]
    (defonce chsk-server
      (sente/make-channel-socket-server!
        (get-sch-adapter)
        {:packer packer})))

  (let [{:keys [ch-recv send-fn connected-uids
                ajax-post-fn ajax-get-or-ws-handshake-fn]}
        chsk-server]
    (defonce ring-ajax-post                ajax-post-fn)
    (defonce ring-ajax-get-or-ws-handshake ajax-get-or-ws-handshake-fn)
    (defonce ch-chsk                       ch-recv)
    (defonce chsk-send!                    send-fn)
    (defonce connected-uids                connected-uids))

  (defonce router
    (sente/start-chsk-router! ch-chsk event-msg-handler)))
