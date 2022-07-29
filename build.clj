(ns build
  (:require [clojure.tools.build.api :as b]
            [clojure.string :as str]
            [org.corfield.build :as bb]))

(def lib 'com.github.blasterai/clj-fstring)
(def current-version (slurp ".version"))

(defn deploy "Deploy the JAR to Clojars." [opts]
  (println "Deploying version" current-version)
  (-> opts
      (assoc :lib lib :version current-version)
      (bb/deploy)))

(defn tests [opts]
  (bb/run-tests opts))

(defn eastwood "Run Eastwood." [opts]
  (-> opts (bb/run-task [:eastwood])))

(defn ci "Run the CI pipeline of tests (and build the JAR)."
  [opts]
  (-> opts
      (assoc :lib lib
             :version current-version
             :scm {:url "https://github.com/blasterai/clj-fstring"
                   :connection "scm:git:git://github.com/blaster-ai/clj-fstring.git"
                   :developerConnection "scm:git:ssh://git@github.com/blasterai/clj-fstring.git"
                   :tag current-version})
      (bb/run-tests)
      (eastwood)
      (bb/clean)
      (bb/jar)))

(defn update-readme [version]
  (let [readme (slurp "README.md")]
    (->> (str/replace readme #"\d+.\d+.\d+" version)
         (spit "README.md"))))

(defn version
  "Bumps version in the .version file. Accepts :bump [:major :minor :patch]"
  [& {:keys [bump]}]
  (if-not bump
    (println current-version)
    (let [[major minor patch] (map parse-long (str/split current-version #"\."))
          new-version (->> (case bump
                             :major [(inc major) 0 0]
                             :minor [major (inc minor) 0]
                             :patch [major minor (inc patch)])
                           (str/join "."))]
      (spit ".version" new-version)
      (update-readme new-version)
      (println new-version))))

(comment
  (bump-version :bump :minor)
  (ci {})
  (deploy))
