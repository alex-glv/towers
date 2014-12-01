(ns dev.core
  (:require [clojure.tools.namespace.repl :refer [refresh]]
            [cemerick.austin]
            [cemerick.austin.repls :refer [browser-connected-repl-js]]
            [compojure.route :refer [resources]]
            ring.adapter.jetty
            [net.cgrand.enlive-html :as enlive]
            [compojure.core :refer [GET defroutes]]
            [clojure.java.io :as io]
            [cljs.closure :refer [build]]))

(defn -main []
  (println "dev ns loaded!"))


(def config (read-string (slurp (io/resource "config.clj"))))

(defn exec-on-dir [call-this dir opts]
  (let [in-except? (fn [file]
                     (not (empty?
                           (filter #(= % file) (:except opts)))))]
    (if (not (.isDirectory dir))
      (when-not (in-except? (.getName dir))
        (call-this dir))
      (doseq [file-r (.listFiles dir)]
        (exec-on-dir call-this file-r opts)))))

(defn build-cljs []
  (exec-on-dir io/delete-file
             (-> (io/resource "public/js/")
                 io/as-file)
             {:except ["pixi.js"]}) ;; todo: extract except from config externs 
  (if-let [cljs-conf (:cljsbuild config)]
    (build (:source-path cljs-conf) (:compiler cljs-conf))))

(defn start-cljs-repl
  ([handler]
     (cemerick.austin.repls/cljs-repl handler))
  ([] (start-cljs-repl (reset! cemerick.austin.repls/browser-repl-env
                               (cemerick.austin/repl-env :host "devbox.planetarium" :port 9000 )))))

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
  (def ^:private server (atom
                         (ring.adapter.jetty/run-jetty #'site {:port 8080 :join? false})))
  ;; (start-cljs-repl)
  server)

(defn cleanup-refresh []
  (.stop @server)
  (reset! server nil)
  (cemerick.austin/stop-server)
  (refresh))
