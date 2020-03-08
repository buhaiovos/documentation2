#!/bin/bash

source ./set-env.sh
java --enable-preview -Xshare:dump -XX:SharedClassListFile=cds-list.lst -XX:SharedArchiveFile=cds-arch.jsa --illegal-access=deny -classpath ../target/backend-0.1.jar

