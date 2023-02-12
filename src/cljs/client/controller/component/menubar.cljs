(ns client.controller.component.menubar)


(defn menubar []
  [:div.menubar
   [:button.menubar-file "File"]
   [:button.menubar-edit "Edit"]
   [:button.menubar-help "Help"]])