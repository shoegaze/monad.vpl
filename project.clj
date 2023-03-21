(defproject plenum-vpl "0.1.0-SNAPSHOT"
  :description "Graphics Visual Programming Language"
  :url "https://github.com/shoegaze/plenum-vpl"
  :license {:name "The MIT License (MIT)"
            :url "https://mit-license.org/"}

  :source-paths ["src/clj"
                 "src/cljc"
                 "src/cljs"]

  :repl-options {:init-ns server.core}

  :dependencies [; Server
                 [org.clojure/clojure       "1.11.1"]
                 [org.clojure/tools.reader  "1.3.6"]
                 [ring                      "1.9.6"]
                 [ring/ring-defaults        "0.3.4"]
                 [compojure                 "1.7.0"]
                 [clojail                   "1.0.6"]
                 ; > Head
                 [seesaw                    "1.5.0"]
                 ; Client
                 [org.clojure/clojurescript "1.11.60"]
                 [org.slf4j/slf4j-nop       "2.0.5"]
                 [cljsjs/react              "18.2.0-0"]
                 [cljsjs/react-dom          "18.2.0-0"]
                 [reagent                   "1.2.0"]
                 [hiccup                    "1.0.5"]
                 ; Shared
                 [org.clojure/core.async    "1.6.673"]
                 [com.taoensso/timbre       "6.1.0"]
                 [com.taoensso/sente        "1.17.0"]
                 [http-kit                  "2.6.0"]]

  :plugins [[lein-cljsbuild "1.1.8"]]

  :cljsbuild {:builds
              {:dev
               {:source-paths ["src/cljs"]
                :compiler {:output-dir "resources/public/js/bundle/dev/"
                           :output-to  "resources/public/js/bundle/bundle.dev.js"
                           :source-map "resources/public/js/bundle/bundle.dev.js.map"
                           :optimizations :whitespace}}
               :pre
               {:source-paths ["src/cljs"]
                :compiler {:output-dir "resources/public/js/bundle/pre/"
                           :output-to  "resources/public/js/bundle/bundle.pre.js"
                           :optimizations :simple
                           :pretty-print true}}
               :prod
               {:source-paths ["src/cljs"]
                :compiler {:output-dir "resources/public/js/bundle/prod/"
                           :output-to  "resources/public/js/bundle/bundle.prod.js"
                           :optimizations :advanced
                           :pretty-print false}}}}

  :clean-targets ^{:protect false} [:target-path "resources/public/js/bundle/"]

  :aliases {; Server
            "serve" ["run" "-m" "server.core"]
            "serve-repl" ["trampoline" "cljsbuild" "repl-listen"]
            ; Client
            "client-rebuild-all" ["do" "clean," "cljsbuild" "once"]
            "client-watch-all" ["cljsbuild" "auto"]
            "client-build-dev" ["cljsbuild" "once" "dev"]
            "client-watch-dev" ["cljsbuild" "auto" "dev"]
            "client-build-pre" ["cljsbuild" "once" "pre"]
            "client-watch-pre" ["cljsbuild" "auto" "pre"]
            "client-build-prod" ["cljsbuild" "once" "prod"]
            "client-watch-prod" ["cljsbuild" "auto" "prod"]})
