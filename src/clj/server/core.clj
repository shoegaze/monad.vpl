(ns server.core
  (:require [server.head.core :as head]))


(defn -main [& _args]
  (println "Starting server...")
  (head/start-head))