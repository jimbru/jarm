(defproject jarm "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[com.cemerick/pomegranate "0.3.0" :exclusions [org.codehaus.plexus/plexus-utils]]
                 [com.palletops/cli-cmds "0.1.1"]
                 [ext "0.1.2"]
                 [grimradical/clj-semver "0.3.0" :exclusions [org.clojure/clojure]]
                 [me.raynes/fs "1.4.6"]
                 [org.clojure/clojure "1.6.0"]
                 [org.clojure/tools.cli "0.3.1"]
                 [org.codehaus.plexus/plexus-utils "2.0.6"]]  ;; Pomegranate dependency
  :main ^:skip-aot jarm.main
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
