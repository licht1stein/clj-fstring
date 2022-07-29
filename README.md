# clj-fstring
[![Clojars Version](https://img.shields.io/clojars/v/com.github.blasterai/clj-fstring)](https://clojars.org/com.github.blasterai/clj-fstring)

Python's f-string for Clojure!

After switching to Clojure the only thing I really missed was the f-string syntax. Remember?

```python
name = "John Smith"
print(f'Hello, {name}!')
>>> Hello, John Smith!
```

Real handy. So as an excercise, I made the same for Clojure:

```clojure
(require [blaster.clj-fstring :refer [f-str]])

(def who "John Smith")
(f-str "Hello, {who}!") ;; => "Hello, John Smith!"
```

`f-string` has no dependencies, only the standard library.

## Installation

You can install from [Clojars](https://clojars.org/com.github.blasterai/clj-fstring):


```clojure
{com.github.blasterai/clj-fstring {:mvn/version "1.1.1"}}
```

## Usage

Some examples, including escape syntax:

```clojure
  (require [blaster.clj-fstring :refer [f-str]]

  (def who "John Smith")
  (f-str "Hello, {who}!") ;; => "Hello, John Smith!"

  ;; It also works with arbitrary expressions
  (f-str "1 + 1 = {(+ 1 2)}") ;; => "1 + 1 = 3"

  ;; And it has a simple escape syntax in case you actually need the curly brackets
  (f-str "This is not evaluated '{spam}");; => "This is not evaluated {spam}"

  (let [where "Sparta"]
    (f-str "This is {where}!"))
```

## Development

Run the project's tests (they'll fail until you edit them):

    $ clojure -T:build test

Lint with Eastwood:

    $ clojure -T:build eastwood

Build a deployable jar of this library:

    $ clojure -T:build ci

Bump version:

    $ clojure -T:build bump-version :bump :patch

Deploy to Clojars:

    $ clojure -T:build deploy

Your library will be deployed to com.github.blasterai/clj-fstring.


## License

Copyright © 2021 blaster.ai

Distributed under the Eclipse Public License version 1.0.
