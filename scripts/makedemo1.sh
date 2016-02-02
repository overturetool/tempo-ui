#! /bin/bash

cd $(git rev-parse --show-toplevel) # go to repo root
mvn -Pdesktop clean package -f demo1/java/pom.xml &&
unzip -o -d demo1/model/lib/ demo1/java/client/target/uidemo-1.0-SNAPSHOT-javafx.zip &&
echo "jar updated"
