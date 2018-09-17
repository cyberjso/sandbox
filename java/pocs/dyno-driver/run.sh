#!/bin/bash

export CLUSTER_NAME="myclusterName"

#ip1,az,token;ip2,az,token;
export CLUSTER_NODES="2.2.2.2,us-west-2a,1234"


./gradlew clean run
