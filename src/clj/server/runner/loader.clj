(ns server.runner.loader
  (:require [clojure.java.io :as io]
            [clojure.data.json :as json]
            [taoensso.timbre :as timbre]
            [node.node-data :refer [->NodeData]]
            [node.node-cache :refer [add-entry]]))


(defn parse-meta [meta-str]
  (json/read-str meta-str :key-fn :keyword))

(defn load-node! [node-cache package node]
  (let [meta       (io/file node "node.json")
        meta-str   (slurp meta)
        meta-json  (parse-meta meta-str)                    ; TODO: Validate json
        script     (io/file node "core.clj")                ; TODO: Load dependencies, too
        script-str (slurp script)
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
          node-name    (.getName node)
          full-path (str package-name "." node-name)
          id        (hash full-path)
          ; HACK: Dangerous without validation!
          node-data (->NodeData full-path meta-json script-str {:small  icon-small
                                                                :medium icon-medium
                                                                :large  icon-large})]
      (timbre/warn "Loading node ( full-path:" full-path "id:" id ") without validation!")
      (swap! node-cache add-entry node-data))))

; TODO: Recursively read packages until max-depth
(defn load-package! [node-cache package]
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


(defn parse-graph [graph-str]
  (json/read-str graph-str :key-fn :keyword))

(defn load-graph! [node-graph graph]
  (timbre/warn "Loading graph:" (.toString graph) "without validation!")
  (let [graph-str  (slurp graph)
        graph-json (parse-graph graph-str)]
    ; TODO: Validate graph json
    (reset! node-graph graph-json)))
