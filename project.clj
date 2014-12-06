(defproject towers "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2371"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [compojure "1.2.1"]
                 [domina "1.0.2"]
                 [ring "1.2.2"]
                 [enlive "1.1.5"]
                 [weasel "0.4.3-SNAPSHOT"]
                 [com.cemerick/piggieback "0.1.3"]]
  
  :source-paths ["src/clj" "src/cljs"]
  :plugins [[cider/cider-nrepl "0.8.1"]
            [lein-cljsbuild "1.0.3"]]

  :repl-options { :init-ns dev.core
                 ;; :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]
                 }
  :cljsbuild {:builds
              [{:source-paths ["src/cljs"],
                :compiler
                {:output-dir "resources/public/js/out",
                 :externs ["resources/public/js/pixi.js"],
                 :optimizations :none,
                 :output-to "resources/public/js/towers.js"
                 }}]})
