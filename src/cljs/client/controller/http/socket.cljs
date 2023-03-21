(ns client.controller.http.socket
  (:require [taoensso.sente :as sente]
            [client.controller.http.handler :refer [event-msg-handler]]))


(declare chsk
         ch-chsk
         chsk-send!
         chsk-state
         router)

(defn start-router [csrf-token]
  (let [type   :auto
        packer :edn
        {:keys [chsk ch-recv send-fn state]}
        (sente/make-channel-socket-client!
          "/chsk"
          csrf-token
          {:type   type
           :packer packer})]
    (defonce chsk       chsk)
    (defonce ch-chsk    ch-recv)
    (defonce chsk-send! send-fn)
    (defonce chsk-state state))

  (defonce router
    (sente/start-chsk-router! ch-chsk event-msg-handler)))
