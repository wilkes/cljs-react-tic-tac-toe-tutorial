(ns wilkes.ttt.app.main
  (:require-macros
    [wilkes.ttt.app.hicada :refer [html]])
  (:require
    [wilkes.ttt.app.react :as react]))

(enable-console-print!)

(defn Square [{:keys [value onClick]}]
  (html [:button.square
         {:onClick (fn [] (onClick))}
         value]))

(defn Board [props]
  (let [[state update-state]
        (react/use-state {:squares (vec (repeat 9 nil))
                          :is-x-next true})

        _ (pr state)

        handle-click
        (fn [i]
          (let [x? (:is-x-next state)]
            (update-state (-> state
                              (update :squares assoc i (if x? "X" "O"))
                              (assoc :is-x-next (not x?))))))

        render-square
        (fn [i]
          (Square {:value (-> state :squares (nth i))
                   :onClick #(handle-click i)}))

        status (str "Next player: " (if (-> state :is-x-next) "X" "O"))]
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
