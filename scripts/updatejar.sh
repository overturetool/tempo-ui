#! /bin/bash

cd $(git rev-parse --show-toplevel) # go to repo root
mvn -Pdesktop clean package -f java/client/pom.xml &&
unzip -o -d model/lib/ java/client/target/uidemo-1.0-SNAPSHOT-javafx.zip &&
echo "jar updated"
