(ns blaster.clj-fstring
  (:require
   [clojure.string :as str]
   [clojure.edn :as edn]))


(defn- escaped?
  [s index]
  (if (neg? (- index 1))
    false
    (= "'" (subs s (dec index) index))
    ))

(comment
  (escaped? "fff '{foo}" 5)
  )


(defn- f-first-brackets-index
  [s]
  (let [curly-open (str/index-of s "{") 
        curly-close (str/index-of s "}")]
    (cond
      (and (not curly-open) (not curly-close)) nil
      (and (some? curly-open) (not curly-close)) (throw (ex-info "Curly brackets mismatch" {:string s}))
      (and (not curly-open) (some? curly-close)) (throw (ex-info "Curly brackets mismatch" {:string s}))
      :else [curly-open curly-close ])
    ))

(defn- f-string-prepare
  ([s]
   (f-string-prepare s []))
  ([s acc]
   (if-let [indeces (f-first-brackets-index s)]
     (let [curly-open (first indeces)
           curly-close (last indeces)
           start-s (subs s 0 curly-open)
           rest-s (subs s (inc curly-close))
           sym (subs s (inc curly-open) curly-close)]
       (if (escaped? s curly-open)
         (recur rest-s (concat acc [(str/replace (subs s 0 (inc curly-close)) "'{" "{")]))
         (recur rest-s (concat acc [start-s (edn/read-string sym)]))))
     (concat acc s))))

(defmacro f-str
  [s]
  `(str ~@(f-string-prepare s)))

