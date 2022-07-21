(ns build
  (:require [clojure.tools.build.api :as b]
            [org.corfield.build :as bb]))

(def lib 'com.github.blasterai/clj-fstring)
(def version (format "1.0.%s" (b/git-count-revs nil)))

(defn deploy "Deploy the JAR to Clojars." [opts]
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

(comment
  ()
  (ci {})
  (deploy))
