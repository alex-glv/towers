(defproject test-cljs "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2371"]
                 [compojure "1.2.1"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]]
    
  :source-paths ["src/clj" "src/cljs"]
  :plugins [[lein-cljsbuild "1.0.3"]
            [lein-ring "0.8.12"]]
  :ring {:handler towers.core/handler}
  :cljsbuild {:builds
              [{;; CLJS source code path
                :source-paths ["src/cljs"]
                ;; Google Closure (CLS) options configuration
                :compiler {;; CLS generated JS script filename
                           :output-to "resources/public/js/towers.js"
                           ;; minimal JS optimization directive
                           :optimizations :none
                           :source-map true
                           ;; generated JS code prettyfication
                           :pretty-print true}}]})
