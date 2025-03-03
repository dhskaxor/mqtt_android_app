#!/bin/sh
chmod +x gradlew
chmod +x gradle/wrapper/gradle-wrapper.jar
set -e
DIR="$( cd "$( dirname "$0" )" && pwd )"
exec "$DIR/gradle/wrapper/gradle-wrapper.jar" "$@"
