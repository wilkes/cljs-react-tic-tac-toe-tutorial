(ns wilkes.ttt.app.factory
  (:require
    [goog.object :as gobj]
    ["react" :as React]))

(defn component-factory
  "lifted from https://github.com/codxse/cljs-mui/blob/bae2fec6755139dd94080f06cafb0b11cb1c431e/src/id/nadiar/cljs_mui/utils.cljs"
  ([react-class args]
   (let [fargs (first args)
         [attr & nodes] (if (or (map? fargs)
                                (nil? fargs))
                          args
                          (cons {} args))]
     (apply (.-createElement React) react-class (clj->js attr) nodes)))
  ([root-obj type args]
   (component-factory (gobj/get root-obj type) args)))
