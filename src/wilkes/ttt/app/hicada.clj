(ns wilkes.ttt.app.hicada
  (:require hicada.compiler))

(defmacro html
  [body]
  (hicada.compiler/compile body
                           {:create-element 'wilkes.ttt.app.react/create-element}))
