(ns wilkes.ttt.app.main
  (:require-macros
    [wilkes.ttt.app.hicada :refer [html]])
  (:require
    [wilkes.ttt.app.react :as react]))

(enable-console-print!)

(defn Square [{:keys [value]}]
  (let [[clicked? set-clicked] (react/use-state nil)]
    (html [:button.square
           {:onClick (fn [] (set-clicked "X"))}
           clicked?])))

(defn render-square [i]
  (Square {:value i}))

(defn Board [props]
  (let [status "Next player: X"]
    (html
      [:div
       [:div.status status]
       [:div.board-row
        (render-square 0)
        (render-square 1)
        (render-square 2)]
       [:div.board-row
        (render-square 3)
        (render-square 4)
        (render-square 5)]
       [:div.board-row
        (render-square 6)
        (render-square 7)
        (render-square 8)]])))

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
