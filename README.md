# clj-fstring

Python's f-string for Clojure!

After switching to Clojure the only thing I really missed was the f-string syntax. Remember?

```python
name = "John Smith"
print(f'Hello, {name}!')
>>> Hello, John Smith!
```

Real handy. So as an excercise, I made the same for Clojure:

```clojure
(def who "John Smith")
#f/str "Hello, {who}!" ;; => "Hello, John Smith!" 
```

`f-string` has no dependencies, only the standard library. 

## Usage

I really like using it with a reader literal:

```clojure
  (def who "John Smith")
  #f/str "Hello, {who}!" ;; => "Hello, John Smith!" 

  ;; It also works with arbitrary expressions
  #f/str "1 + 1 = {(+ 1 2)}" ;; => "1 + 1 = 3"

  ;; And it has a simple escape syntax in case you actually need the curly brackets
  #f/str "This is not evaluated '{spam}";; => "This is not evaluated {spam}"

  (def where "Sparta")
  (f-string "This is {where}!")
```

But if you insist, you can just require the `f-string` function:

```clojure
(require [blaster.clj-fstring :refer [f-string]])

(def where "Sparta")
(f-string "This is {where}!")
```

## Development

Invoke a library API function from the command-line:

    $ clojure -X blasterai.clj-fstring/foo :a 1 :b '"two"'
    {:a 1, :b "two"} "Hello, World!"

Run the project's tests (they'll fail until you edit them):

    $ clojure -X:test:runner

Build a deployable jar of this library:

    $ clojure -X:jar

This will update the generated `pom.xml` file to keep the dependencies synchronized with
your `deps.edn` file. You can update the version (and SCM tag) information in the `pom.xml` using the
`:version` argument:

    $ clojure -X:jar :version '"1.2.3"'

Install it locally (requires the `pom.xml` file):

    $ clojure -X:install

Deploy it to Clojars -- needs `CLOJARS_USERNAME` and `CLOJARS_PASSWORD` environment
variables (requires the `pom.xml` file):

    $ clojure -X:deploy

Your library will be deployed to io.github.blasterai/clj-fstring on clojars.org by default.

If you don't plan to install/deploy the library, you can remove the
`pom.xml` file but you will also need to remove `:sync-pom true` from the `deps.edn`
file (in the `:exec-args` for `depstar`).
    
## License

Copyright © 2021 blaster.ai

Distributed under the Eclipse Public License version 1.0.
