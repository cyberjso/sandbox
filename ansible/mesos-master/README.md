# Apache Mesos Provisioning

This project aims to provision everything needed in order to run Apache Mesos in a cluster topology. The recipes currently assumes  Zookeeper an Mesos master are running on the same machine, but they can be easily refactored in order to do differently. 
It's needed have ansible up and running before use It. I'd adivce follow this tutorial. http://docs.ansible.com/intro_getting_started.html

## Pre conditions
* Ubuntu 14.04
* ssh keys from where those ansible recipes will run property set on the target machines

## Post conditions
* In order to have mesos + zookeeper up and running, be sure you follow the configuration available on this tutorial: http://open.mesosphere.com/getting-started/datacenter/install/ . These recipes only install the packages and libs needed but not configure them.
