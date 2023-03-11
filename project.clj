(defproject plenum-vpl "0.1.0-SNAPSHOT"
  :description "Graphics Visual Programming Language"
  :url "https://github.com/shoegaze/plenum-vpl"
  :license {:name "The MIT License (MIT)"
            :url "https://mit-license.org/"}

  :source-paths ["src/clj"
                 "src/cljc"
                 "src/cljs"]

  :dependencies [[org.clojure/clojure "1.11.1"]             ; Server
                 [org.clojure/core.async "1.6.673"]
                 [ring/ring-core "1.9.5"]
                 [ring/ring-jetty-adapter "1.9.5"]
                 [ring/ring-defaults "0.3.4"]
                 [cheshire "5.11.0"]
                 [clojail "1.0.6"]
                 [seesaw "1.5.0"]                           ; > Head
                 [org.clojure/clojurescript "1.11.57"]      ; Client/Controller
                 [compojure "1.7.0"]
                 [hiccup "1.0.5"]
                 [cljsjs/react "17.0.2-0"]
                 [cljsjs/react-dom "17.0.2-0"]
                 [reagent "1.1.1"]
                 [com.taoensso/timbre "6.1.0"]              ; Shared
                 ]

  :plugins [[lein-cljsbuild "1.1.8"]]

  :cljsbuild {:builds
              {:dev
               {:source-paths ["src/cljs"]
                :compiler {:output-dir "resources/public/js/bundle"
                           :output-to "resources/public/js/bundle/bundle.dev.js"
                           :optimizations :whitespace
                           :pretty-print true
                           :source-map "resources/public/js/bundle/bundle.dev.js.map"}}
               :pre
               {:source-paths ["src/cljs"]
                :compiler {:output-to "resources/public/js/bundle/bundle.pre.js"
                           :optimizations :simple
                           :pretty-print false}}
               :prod
               {:source-paths ["src/cljs"]
                :compiler {:output-to "resources/public/js/bundle/bundle.prod.js"
                           :optimizations :advanced
                           :pretty-print false}}}}

  :clean-targets ^{:protect false} [:target-path "resources/public/js/bundle/"]

  :aliases {"serve" ["run" "-m" "server.core"]                      ; Server
            "serve-repl" ["trampoline" "cljsbuild" "repl-listen"]
            "client-rebuild-all" ["do" "clean," "cljsbuild" "once"] ; Client
            "client-watch-all" ["cljsbuild" "auto"]
            "client-build-dev" ["cljsbuild" "once" "dev"]
            "client-watch-dev" ["cljsbuild" "auto" "dev"]
            "client-build-pre" ["cljsbuild" "once" "pre"]
            "client-watch-pre" ["cljsbuild" "auto" "pre"]
            "client-build-prod" ["cljsbuild" "once" "prod"]
            "client-watch-prod" ["cljsbuild" "auto" "prod"]}

  :repl-options {:init-ns server.core})
