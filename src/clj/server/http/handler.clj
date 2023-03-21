(ns server.http.handler
  (:require [taoensso.timbre :as timbre]
            [server.http.socket :refer [chsk-send!]]
            [server.state :as state]))


(defmulti event-msg-handler :id)

(defmethod event-msg-handler :default [{:as ev-msg :keys [event]}]
  (timbre/warn "Unhandled event:" event))

(defmethod event-msg-handler :chsk/uidport-open [{:keys [uid client-id]}]
  (timbre/info "Connection opened:" uid client-id))

(defmethod event-msg-handler :chsk/uidport-close [{:keys [uid client-id]}]
  (timbre/info "Connection closed:" uid client-id))

(defmethod event-msg-handler :chsk/ws-ping [_])


(defmethod event-msg-handler :vpl/get-nodes [{:as ev-msg :keys [?reply-fn]}]
  (timbre/info "Server event: :vpl/get-nodes")
  (when ?reply-fn
    (?reply-fn {:hello "World"})
    ;(?reply-fn @state/node-cache)
    ))

(defmethod event-msg-handler :vpl/get-graph [{:as ev-msg :keys [?reply-fn]}]
  (timbre/info "Server event: :vpl/get-graph")
  ; TODO: REPLY
  (when ?reply-fn
    (?reply-fn {:goodbye "World"})
    ;(?reply-fn @state/node-graph)
    ))
