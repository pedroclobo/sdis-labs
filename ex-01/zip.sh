#!/bin/sh

mvn clean
[ -f "ex-01.zip" ] && rm -rf ex-01.zip
zip -r ex-01.zip . -x zip.sh "*.git*"
