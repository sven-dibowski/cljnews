(ns core
  (:require [jsonista.core :as j]
            [clojure.term.colors :as tc]))

(defn get-api-key []
  (-> "config.edn"
      slurp
      read-string
      :api-key))

(def url (str "https://newsapi.org/v2/top-headlines?country=us&apiKey=" (get-api-key)))

(defn send-request [url]
  (slurp url))

(defn request [url]
  (-> (send-request url)
      (j/read-value j/keyword-keys-object-mapper)))

(defn print-results [{:keys [articles] :as results}]
  (doseq [{:keys [title url]} articles]
    (println (tc/yellow "> " title))
    (println "  + " url)
    (println " ")))

(defn -main []
  (print-results (request url)))
