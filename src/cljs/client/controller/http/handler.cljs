(ns client.controller.http.handler
  (:require [taoensso.timbre :as timbre]
            [client.controller.state :as state]))


(defmulti event-msg-handler :id)

(defmethod event-msg-handler :default [{:keys [event]}]
  (timbre/warn "Unhandled event:" event))

(defmethod event-msg-handler :chsk/state
  [{:keys [?data]}]
  (let [[_old-state-map new-state-map] ?data]
    (cond
      (:first-open? new-state-map) (reset! state/socket-open? true)
      (:opened?     new-state-map) (reset! state/socket-open? true)
      (:closed?     new-state-map) (reset! state/socket-open? false)
      :else                        (timbre/debug "Channel socket state changed:" new-state-map))))

(defmethod event-msg-handler :chsk/recv
  [{:keys [?data]}]
  (timbre/debug "Push event from server:" ?data))

(defmethod event-msg-handler :chsk/handshake
  [{:keys [?data]}]
  (let [[_ _ _ _] ?data]
    (timbre/debug "Handshake:" ?data)))
