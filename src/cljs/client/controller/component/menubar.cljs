(ns client.controller.component.menubar)


(defn menubar []
  [:div.menubar
   [:input.menubar-file {:type "button"
                         :value "File"
                         :on-click #(js/alert "TODO: File")}]
   [:input.menubar-edit {:type "button"
                         :value "Edit"
                         :on-click #(js/alert "TODO: Edit")}]
   [:input.menubar-help {:type "button"
                         :value "Help"
                         :on-click #(js/alert "TODO: Help")}]])