(ns dev.core
  (:require [clojure.tools.namespace.repl :refer [refresh]]
            [cemerick.austin]
            [cemerick.austin.repls :refer [browser-connected-repl-js]]
            [compojure.route :refer [resources]]
            ring.adapter.jetty
            [net.cgrand.enlive-html :as enlive]
            [compojure.core :refer [GET defroutes]]
            [clojure.java.io :as io]))

(defn -main []
  (println "dev ns loaded!"))

(defn start-cljs-repl
  ([handler]
     (cemerick.austin.repls/cljs-repl handler))
  ([] (start-cljs-repl (reset! cemerick.austin.repls/browser-repl-env
                               (cemerick.austin/repl-env :host "devbox.planetarium" :port 9000)))))

(enlive/deftemplate page
  (io/resource "public/index.html")
  []
  [:body] (enlive/append
            (enlive/html [:script (browser-connected-repl-js)])))

(defroutes site
  (resources "/")
  (GET "/" request (page)))

(defn run
  []
  (cemerick.austin/start-server 9000)
  (def ^:private server
    (ring.adapter.jetty/run-jetty #'site {:port 8080 :join? false}))
  ;; (start-cljs-repl)
  server) 

(defn cleanup-refresh []
  (.stop server)
  (cemerick.austin/stop-server)
  (refresh))
