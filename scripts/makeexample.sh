#! /bin/bash

cd $(git rev-parse --show-toplevel) # go to repo root
mvn clean package -f example/pom.xml &&
unzip -o -d example/model/lib/ example/client/target/example-1.0-SNAPSHOT-javafx.zip &&
echo "done"
