{:cljsbuild {:source-path "src/cljs/towers/"
             :compiler {
                        :output-to "resources/public/js/towers.js"
                        :output-dir "resources/public/js"
                        :externs ["resources/public/js/pixi.js"]
                        :optimizations :whitespace}}}
