(ns wilkes.ttt.app.main
  (:require-macros
    [wilkes.ttt.app.hicada :refer [html]])
  (:require
    [wilkes.ttt.app.react :as react]))

(enable-console-print!)

(def lines [[0 1 2]
            [3 4 5]
            [6 7 8]
            [0 3 6]
            [1 4 7]
            [2 5 8]
            [0 4 8]
            [2 4 6]])

(defn calculate-winner [squares]
  (first (keep (fn [[a b c]]
                 (when (and (nth squares a)
                            (= (nth squares a)
                               (nth squares b))
                            (= (nth squares a)
                               (nth squares c)))
                   (nth squares a)))
               lines)))

(defn Square [{:keys [value onClick]}]
  (html [:button.square {:onClick onClick} value]))

(defn Board [_]
  (let [[state set-state]
        (react/use-state {:squares (vec (repeat 9 nil))
                          :is-x-next true})

        handle-click
        (fn [i]
          (let [x? (:is-x-next state)
                winner (calculate-winner (:squares state))]
            (when-not (or winner (nth (:squares state) i))
              (set-state (-> state
                             (update :squares assoc i (if x? "X" "O"))
                             (assoc :is-x-next (not x?)))))))

        render-square
        (fn [i]
          (Square {:value (-> state :squares (nth i))
                   :onClick #(handle-click i)}))

        winner (calculate-winner (:squares state))
        status (if winner
                 (str "Winner: " winner)
                 (str "Next player: " (if (-> state :is-x-next) "X" "O")))]
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

(defn Game [_]
  (html
    [:div.game
     [:div.game-board
      (Board nil)]
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
