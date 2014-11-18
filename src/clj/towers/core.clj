(ns towers.core
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.server.standalone :as ring-server]
            [ring.middleware file-info file]
            ;;[clojure.tools.nrepl.server :as nrepl-server]
            ;;[cider.nrepl :refer (cider-nrepl-handler)]
            ))

(def repl-env (reset! cemerick.austin.repls/browser-repl-env
                      (cemerick.austin/repl-env)))
(defn start-cljs-repl
  ([handler] (cemerick.austin.repls/cljs-repl handler))
  ([] (start-cljs-repl repl-env)))

(defroutes app-routes
  ; to serve document root address
  (GET "/" [] "<p>Hello from compojure</p>")
  ; to serve static pages saved in resources/public directory
  (route/resources "/")
  ; if page is not found
  (route/not-found "Page not found"))

;; site function creates a handler suitable for a standard website,
;; adding a bunch of standard ring middleware to app-route:
(def handler
  (handler/site app-routes))

(defonce server (atom nil))

(defn server-start
  "used for starting the server in development mode from REPL"
  [& [port]]
  (let [port (if port (Integer/parseInt port) 3000)]
    (reset! server
            (ring-server/serve handler
                   {:port port
                    :open-browser? false
                    :auto-reload? true
                    :join true}))
    (println (cemerick.austin.repls/browser-connected-repl-js))
    (println (str  "You can view the site at http://localhost:" port))))

(defn server-stop []
  (.stop @server)
  (reset! server nil))
