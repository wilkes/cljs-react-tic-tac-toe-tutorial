(ns wilkes.ttt.app.react
  (:require
    ["react" :as React]
    ["react-dom" :as ReactDOM]
    [wilkes.ttt.app.factory :as cf]))


(def create-element (.-createElement React))

(defn el
  [elem & args]
  (let [fargs (first args)
        [props & children] (if (or (map? fargs)
                                   (nil? fargs))
                             args
                             (cons {} args))]
    (apply create-element elem (clj->js props) children)))

(defn render [component-class-or-fn element-id]
  (.render ReactDOM
           (el component-class-or-fn nil)
           (. js/document (getElementById element-id))))

(defn use-state [init-value]
  (.useState React init-value))

(defn fragment [& args]
  (cf/component-factory React "Fragment" args))
