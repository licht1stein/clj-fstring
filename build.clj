(ns build
  (:require [clojure.tools.build.api :as b]
            [clojure.string :as str]
            [org.corfield.build :as bb]))

(def lib 'com.github.blasterai/clj-fstring)
(def version (slurp ".version"))

(defn deploy "Deploy the JAR to Clojars." [opts]
  (println "Deploying version" version)
  (-> opts
      (assoc :lib lib :version version)
      (bb/deploy)))

(defn test [opts]
  (bb/run-tests opts))

(defn eastwood "Run Eastwood." [opts]
  (-> opts (bb/run-task [:eastwood])))

(defn ci "Run the CI pipeline of tests (and build the JAR)."
  [opts]
  (-> opts
      (assoc :lib lib :version version)
      (bb/run-tests)
      (eastwood)
      (bb/clean)
      (bb/jar)))

(defn update-readme [version]
  (let [readme (slurp "README.md")]
    (->> (str/replace readme #"\d+.\d+.\d+" version)
         (spit "README.md"))))

(defn bump-version
  "Bumps version in the .version file. Accepts :bump [:major :minor :patch]"
  [{:keys [bump]}]
  (let [[major minor patch] (map parse-long (str/split version #"\."))
        new-version (->> (case bump
                           :major [(inc major) 0 0]
                           :minor [major (inc minor) 0]
                           :patch [major minor (inc patch)])
                         (str/join "."))]
    (spit ".version" new-version)
    (update-readme new-version)
    (println new-version)))

(comment
  ()
  (ci {})
  (deploy))
