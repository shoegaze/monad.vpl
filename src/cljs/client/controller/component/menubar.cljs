(ns client.controller.component.menubar)


(defn menubar []
  [:div.menubar
   [:button.menubar-file
    "File"
    {:on-click #(js/console.log "File clicked")}]
   [:button.menubar-edit "Edit"]
   [:button.menubar-help "Help"]])