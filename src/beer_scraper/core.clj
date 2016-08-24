(ns beer-scraper.core
  (:require
    [net.cgrand.enlive-html :as enlive]
    [cheshire.core :as json] ))

(def base-url "http://www.beerforthat.com/beerstyles/")

(defn get-page
  "Retrieves the page we're interested in as a parseable structure"
  [url]
  (enlive/html-resource (java.net.URI. url)))

  (defn extract-urls
  "Gets the URLs we're interested in from the page"
  [page]
  (map #(get-in % [:attrs :href]) (enlive/select page [:.beerstyle_link])))

(defn parse-beer-style
   [beer-style]
   (slurp (str "http://www.beerforthat.com" beer-style)))

(defn aside-to-map [html-text]
  (let [content (enlive/html-resource (java.io.StringReader. html-text))]
        (apply hash-map (mapcat :content (enlive/select content [#{:dt :dd}])))))

(defn get-json
  [url]
  (->> url
      (get-page)
      (extract-urls)
      (map parse-beer-style)
      (map #(json/parse-string %))
      (map #(get % "aside_content"))
      (map aside-to-map)
      (map #(json/generate-string %))
      (map #(spit "mini_beers_aside.json" % :append true))
    ))
