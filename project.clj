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
                 [enlive "1.1.5"]]
  
  :source-paths ["src/clj" "src/cljs"]
  :plugins [[com.cemerick/austin "0.1.5"]
            [cider/cider-nrepl "0.8.1"]]
  :repl-options { :init-ns dev.core })
