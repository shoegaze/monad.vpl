(defproject plenum-vpl "0.1.0-SNAPSHOT"
  :description "Graphics Visual Programming Language"
  :url "https://github.com/shoegaze/plenum-vpl"
  :license {:name "The MIT License (MIT)"
            :url "https://mit-license.org/"}

  :source-paths ["src/clj"
                 "src/cljc"
                 "src/cljs"]

  :dependencies [[org.clojure/clojure "1.11.1"]
                 [seesaw "1.5.0"]]

  :aliases {"gui-start" ["run" "-m" "gui.core"]}

  :repl-options {:init-ns server.core})
