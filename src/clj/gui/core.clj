(ns gui.core
  (:require [seesaw.core :refer :all]
            [gui.controller :as c]
            [gui.viewport :as v]))


(defn -main [& _args]
  (native!)
  (-> (c/c-frame)
      pack!
      show!)
  (-> (v/v-frame)
      pack!
      show!))