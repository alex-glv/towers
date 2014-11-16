(ns towers.debug
  (:require [goog.dom :as dom]
            [goog.date :as date]
            [clojure.string :as string]))

(defn log [& els]
  (let [log-div (dom/getElement "debug")
        dt-fmt (aget  (.split (.toTimeString (js/Date.)) " ") 0)
        log-string (.join (apply array els))
        prnt (dom/createDom "div")
        tm (dom/createDom "span" (js-obj "class" "log-time") (js/String dt-fmt))
        entr (dom/createDom "span" (js-obj "class" "log-entry") log-string)
        ]
    (dom/append prnt tm)
    (dom/append prnt entr)
    (dom/append log-div prnt)
    (set! (.-scrollTop log-div) 10000)))
