#!/bin/sh

mvn clean
[ -f "ex-02.zip" ] && rm -rf ex-02.zip
zip -r ex-02.zip . -x zip.sh "*.git*" "*.venv*"
