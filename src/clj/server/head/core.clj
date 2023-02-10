(ns server.head.core
  (:require [seesaw.core :as s]))


(defn- create-frame []
  (s/frame :title "Render Output"
           :content "Hello, output!"))

(defn start-head [& _args]
  (s/native!)
  (-> (create-frame)
      s/pack!
      s/show!))