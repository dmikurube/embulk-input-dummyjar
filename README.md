# Dummyjar input plugin for Embulk

A dummy plugin to test: https://github.com/embulk/embulk/pull/592

## Overview

* **Plugin type**: input
* **Resume supported**: no
* **Cleanup supported**: no
* **Guess supported**: no

## Configuration

No configuration.

## Example

```yaml
in:
  type: jar:/full/path/to/your/built/jar
```


## Build

```
$ ./gradlew pluginJar
```
