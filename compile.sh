#!/bin/sh
mvn clean verify
cat stub.sh target/GraphEd-1.0-SNAPSHOT.jar > graphed.run && chmod +x graphed.run
rm target -R
