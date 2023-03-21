(ns server.http.handler
  (:require [taoensso.timbre :as timbre]
            [server.state :as state]))


(defmulti event-msg-handler :id)

(defmethod event-msg-handler :default [{:keys [event]}]
  (timbre/warn "Unhandled event:" event))

(defmethod event-msg-handler :chsk/uidport-open [{:keys [uid client-id]}]
  (timbre/info "Connection opened:" uid client-id))

(defmethod event-msg-handler :chsk/uidport-close [{:keys [uid client-id]}]
  (timbre/info "Connection closed:" uid client-id))

(defmethod event-msg-handler :chsk/ws-ping [_])


(defmethod event-msg-handler :vpl/get-nodes [{:keys [?reply-fn]}]
  (timbre/info "Server event: :vpl/get-nodes")
  (when ?reply-fn
    (?reply-fn @state/node-cache)))

(defmethod event-msg-handler :vpl/get-graph [{:keys [?reply-fn]}]
  (timbre/info "Server event: :vpl/get-graph")
  (when ?reply-fn
    (?reply-fn @state/node-graph)))
