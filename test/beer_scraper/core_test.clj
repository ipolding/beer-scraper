(ns beer-scraper.core-test
  (:require [clojure.test :refer :all]
            [beer-scraper.core :refer :all]))

(deftest a-test
  (testing "FIXME, I fail."
    (let [result (extract-urls (get-page "http://www.beerforthat.com/beerstyles/"))]
        (is (> (count result) 0))
    )))
