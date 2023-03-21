(ns client.controller.http.handler
  (:require [taoensso.timbre :as timbre]
            [client.controller.state :as state]))


(defmulti event-msg-handler :id)

(defmethod event-msg-handler :default [{:as ev-msg :keys [event]}]
  (timbre/log "Unhandled event:" event))

(defmethod event-msg-handler :chsk/state
  [{:as ev-msg :keys [?data]}]
  (let [[old-state-map new-state-map] ?data]
    (cond
      (:first-open? new-state-map) (reset! state/socket-open? true)
      (:opened?     new-state-map) (reset! state/socket-open? true)
      (:closed?     new-state-map) (reset! state/socket-open? false)
      :else                        (timbre/debug "Channel socket state changed:" new-state-map))))

(defmethod event-msg-handler :chsk/recv
  [{:as ev-msg :keys [?data]}]
  (timbre/info "Push event from server:" ?data))

(defmethod event-msg-handler :chsk/handshake
  [{:as ev-msg :keys [?data]}]
  (let [[?uid _ ?handshake-data first-handshake?] ?data]
    (timbre/info "Handshake:" ?data)))
