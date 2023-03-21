(ns server.runner.loader
  (:require [clojure.java.io :as io]
            [clojure.data.json :as json]
            [taoensso.timbre :as timbre]
            [shared.node-cache.core :refer [get-node add-model add-view]]
            [shared.node-cache.node-model :refer [->NodeModel]]
            [shared.node-cache.node-view :refer [->NodeView]]
            [shared.graph.core :refer [->Graph]]
            [shared.graph.graph-instance :refer [->GraphInstance]]
            [shared.graph.graph-edge :refer [->GraphEdge]]))


(defn- parse-meta [meta-str]
  (json/read-str meta-str :key-fn keyword))

(defn- load-node! [node-cache package node-file]
  (let [meta-json  (-> node-file
                       (io/file "node.json")
                       slurp
                       parse-meta)
        script-str (-> node-file
                       (io/file "core.clj")
                       slurp)
        ;icons       (io/file node "icons")
        ;icon-small  (slurp (io/file icons "small.png"))     ; TODO: Accept .svg files
        ;icon-medium (slurp (io/file icons "medium.png"))
        ;icon-large  (slurp (io/file icons "large.png"))
        icon-small  nil
        icon-medium nil
        icon-large  nil]
    ; TODO: Validate meta-json
    ; TODO: Validate script-str
    ; TODO: Validate icon-small, icon-medium, icon-large
    (let [package-name (.getName package)
          node-name    (.getName node-file)
          full-path (str package-name "." node-name)
          ; HACK: Dangerous without validation!
          node-model (->NodeModel full-path meta-json script-str)
          node-view  (->NodeView  full-path {} {} {:small  icon-small
                                                   :medium icon-medium
                                                   :large  icon-large})]
      (timbre/warn "Loading node model and view ( full-path:" full-path ") without validation!")
      (swap! node-cache add-model node-model)
      (swap! node-cache add-view  node-view))))

; TODO: Recursively read packages until max-depth
(defn- load-package! [node-cache package]
  (doseq [node (.listFiles package)
          :when (.isDirectory node)]
    (timbre/info "  *" (.getName node))
    (load-node! node-cache package node)))

(defn load-nodes! [node-cache packages-path]
  (let [packages-dir (io/file packages-path)
        packages     (.listFiles packages-dir)]
    (doseq [package packages
            :when (.isDirectory package)]
      (timbre/info " *" (.getName package))
      (load-package! node-cache package))))


(defn- parse-graph [graph-str]
  (json/read-str graph-str :key-fn keyword))

(defn load-graph! [node-cache node-graph graph-file]
  (timbre/warn "Loading graph:" (.toString graph-file) "without validation!")

  (let [; TODO: Validate graph json
        graph-json (-> graph-file slurp parse-graph)
        ; TODO: Verify node-models and node-views exist
        instances  (->> graph-json
                        :instances
                        (map #(let [{instance-id :instance-id
                                     full-path   :full-path} %
                                    {model :model
                                     view  :view} (get-node @node-cache full-path)]
                                ; TODO: Load :view property from instance
                                ; TODO: Verify instance-ids are unique
                                ; TODO: Validate model, view schema
                                (->GraphInstance instance-id model view))))
        edges      (->> graph-json
                        :edges
                        (map #(apply ->GraphEdge %)))
        new-graph  (->Graph instances edges)]

    (reset! node-graph new-graph)))
