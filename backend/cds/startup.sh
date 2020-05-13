#!/bin/bash

source ./set-env.sh
java --enable-preview -XX:SharedArchiveFile=cds-arch.jsa --illegal-access=deny -jar ../target/backend-0.1.jar
