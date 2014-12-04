(ns dev.core
  (:require [clojure.tools.namespace.repl :refer [refresh]]
            [cemerick.piggieback :as piggieback]
            [compojure.route :refer [resources]]
            [weasel.repl.websocket :as weasel]
            ring.adapter.jetty
            [net.cgrand.enlive-html :as enlive]
            [compojure.core :refer [GET defroutes]]
            [clojure.java.io :as io]
            [cljs.closure :refer [build]]))

(defn -main []
  (println "dev ns loaded!"))


(defn browser-repl []
  (piggieback/cljs-repl :repl-env (weasel/repl-env :ip "0.0.0.0" :port 9001)))

(defroutes site
  (resources "/"))

(defn run
  []
  (def ^:private server (atom
                         (ring.adapter.jetty/run-jetty #'site {:port 8080 :join? false})))
  server)

(defn cleanup-refresh []
  (.stop @server)
  (reset! server nil)
  (refresh))
