#!/usr/bin/env nbb
;; Run the suite on the ClojureScript side.
;;
;; Not a formality — opcua.builtin's UInt32/Int32 decode and its Float/
;; Double bit-reinterpretation take genuinely different code paths on the
;; JVM vs. ClojureScript (see that namespace's docstring for the 32-bit-
;; signed-vs-unsigned trap and the DataView-vs-Java-primitive split), and
;; this is what actually proves both sides agree rather than assuming it.
;;
;;   nbb --classpath "$(clojure -A:cljs -Spath)" scripts/verify-cljs.cljs
(ns verify-cljs
  (:require [clojure.test :as t]
            [opcua.core-test]))

(defmethod t/report [:cljs.test/default :end-run-tests] [m]
  (println)
  (if (t/successful? m)
    (println "all checks passed on the ClojureScript path")
    (do (println "FAILED on the ClojureScript path")
        (js/process.exit 1))))

(t/run-tests 'opcua.core-test)
