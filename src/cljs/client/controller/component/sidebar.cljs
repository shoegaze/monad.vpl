(ns client.controller.component.sidebar)


(defn sidebar []
  [:div.sidebar
   [:h1.sidebar-title "Palette"]
   [:div.sidebar-container
    ()
    [:button.sidebar-entry "{{Node 1}}"]
    [:button.sidebar-entry "{{Node 2}}"]
    [:button.sidebar-entry "{{Node 3}}"]]
   [:div.sidebar-info
    [:h1.sidebar-info-title "{{Name}}"]
    [:h2.sidebar-info-metadata "{{Metadata}}"]
    [:div.sidebar-info-description "{{Description}}"]]])