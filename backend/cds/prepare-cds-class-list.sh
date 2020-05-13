#!/bin/bash

source ./set-env.sh
java --enable-preview -XX:DumpLoadedClassList=cds-list.lst --illegal-access=deny -jar ../target/backend-0.1.jar

