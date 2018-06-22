# what-do-you-think
A _Clojure_ app for monitoring a [go-cd](https://www.gocd.org/) pipeline.

This Project was build with [Leiningen](https://leiningen.org/). Please install it! This project uses the
[tesla-microservice](https://github.com/otto-de/tesla-microservice).

The server will automatically run on port 8080. All configurations can be found in `resources/default.edn`.
You can add a sound to play when the build is broken, ```:error-sound``` in the config file.

## Tests
To run the tests:
```bash
lein test
```

## Run
To run the app:
```bash
lein run
```

## Build
To build a jar file:
```bash
lein uberjar
```
This will create a jar file in the `target/uberjar` folder `what-do-you-think-<VERSION>-standalone.jar`
