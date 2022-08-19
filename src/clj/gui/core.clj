(ns gui.core
  (:require [seesaw.core :refer :all]))


(defn -main [& _args]
  (-> (frame :title "Hello"
             :content "Hello, GUI!")
      pack!
      show!))