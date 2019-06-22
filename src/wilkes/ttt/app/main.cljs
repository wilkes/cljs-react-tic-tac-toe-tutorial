(ns wilkes.ttt.app.main
  (:require-macros
    [wilkes.ttt.app.hicada :refer [html]])
  (:require
    [wilkes.ttt.app.react :as react]))

(enable-console-print!)

(defn Square [props]
  (html [:button.square ""]))

(defn render-square [props i]
  (Square props))

(defn Board [props]
  (let [status "Next player: X"]
    (html
      [:div
       [:div.status status]
       [:div.board-row
        (render-square props 0)
        (render-square props 1)
        (render-square props 2)]
       [:div.board-row
        (render-square props 3)
        (render-square props 4)
        (render-square props 5)]
       [:div.board-row
        (render-square props 6)
        (render-square props 7)
        (render-square props 8)]])))

(defn Game [props]
  (html
    [:div.game
     [:div.game-board
      (Board props)]
     [:div.game-info
      [:div]
      [:ol]]]))


(defn start []
  ;; start is called by init and after code reloading finishes
  ;; this is controlled by the :after-load in the config
  (react/render Game "app"))

(defn ^:export init []
  ;; init is called ONCE when the page loads
  ;; this is called in the index.html and must be exported
  ;; so it is available even in :advanced release builds
  (start))

(defn stop []
  ;; stop is called before any code is reloaded
  ;; this is controlled by :before-load in the config
  (js/console.log "stop"))
