(ns server.runner.loader
  (:require [clojure.java.io :as io]
            [clojure.tools.reader.edn :as edn]
            [taoensso.timbre :as timbre]
            [shared.node-cache.core :refer [get-node add-model add-view]]
            [shared.node-cache.node-model :refer [make-node-model]]
            [shared.node-cache.node-view :refer [make-node-view]]
            [shared.graph.core :refer [make-graph]]
            [shared.graph.graph-instance :refer [make-graph-instance]]
            [shared.graph.graph-edge :refer [make-graph-edge]])
  (:refer-clojure :exclude [read read-string]))


(defn- parse-meta [meta-str]
  (edn/read-string meta-str))

; TODO:
(defn- valid-model? [node-model]
  true)

; TODO:
(defn- valid-view? [node-view]
  true)

(defn- load-node! [node-cache package node-file]
  (let [meta-edn   (-> node-file
                       (io/file "node.edn")
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
    ; TODO: Validate meta-edn
    ; TODO: Validate script-str
    ; TODO: Validate icon-small, icon-medium, icon-large
    (let [package-name (.getName package)
          node-name    (.getName node-file)
          full-path (str package-name "." node-name)
          node-model (make-node-model full-path meta-edn script-str)
          node-view  (make-node-view  full-path {} {} {:small  icon-small
                                                       :medium icon-medium
                                                       :large  icon-large})]
      (if (valid-model? node-model)
        (do
          (timbre/warn "> Node model { full-path:" full-path "} loaded without validation!")
          (swap! node-cache add-model node-model))
        (timbre/error "> Could not load node model"))

      (if (valid-view? node-view)
        (do
          (timbre/warn "> Node view { full-path:" full-path "} loaded without validation!")
          (swap! node-cache add-view  node-view))
        (timbre/error "> Could not load node view")))))

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
  (edn/read-string graph-str))

; TODO:
(defn- valid-graph? [graph]
  true)

(defn load-graph! [_node-cache node-graph graph-file]
  (timbre/warn "Loading graph:" (.toString graph-file) "without validation!")

  (let [; TODO: Validate graph schema
        ; TODO: Map graph to use make-*
        graph-edn (-> graph-file
                      slurp
                      parse-graph)]

    (if (valid-graph? graph-edn)
      (do
        (timbre/warn "> Graph loaded:" graph-edn)
        (reset! node-graph graph-edn))
      (timbre/error "> Graph could not be loaded!"))))
