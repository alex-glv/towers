(ns towers.debug
  (:require [goog.dom :as dom]
            [goog.date :as date]
            [clojure.string :as string]))

(defn log [& els]
  (let [log-div (dom/getElement "debug")
        datetime (date/DateTime.)
        datetime-formatted (string/join ":" [(.getHours datetime) (.getMinutes datetime) (.getSeconds datetime)])
        log-string (js/String (string/join " " [datetime-formatted (apply pr-str els)]))
        new-entry (dom/createDom "span" (js-obj nil) log-string)]
    (dom/append log-div new-entry)))
