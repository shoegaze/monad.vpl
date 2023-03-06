(ns server.runner.loader
  (:require [clojure.java.io :as io]
            [taoensso.timbre :as timbre]
            [cheshire.core :as json]))


(defn load-node! [node-cache package node]
  (let [meta       (io/file node "node.json")
        meta-str   (slurp meta)
        meta-json  (json/parse-string meta-str true)        ; TODO: Validate json
        script     (io/file node "core.clj")                ; TODO: Load dependencies, too
        script-str (slurp script)
        icons       (io/file node "icons")
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
          node-data {:full-path full-path
                     :meta      meta-json                   ; HACK: Dangerous!
                     :script    script-str
                     :icons {:small  icon-small
                             :medium icon-medium
                             :large  icon-large}}]
      (timbre/warn "Loading node ( full-path:" full-path "id:" id ") without validation!")
      (swap! node-cache #(assoc % id node-data)))))

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


(defn load-graph! [node-graph graph]
  (timbre/info "Loading graph:" (.toString graph))
  (let [graph-str  (slurp graph)
        graph-json (json/parse-string graph-str true)]
    ; TODO: Validate graph json
    (reset! node-graph graph-json)))
