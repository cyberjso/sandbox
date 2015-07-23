# Word Counter - DRPC Version

This is the same problem that was already solved [here](https://github.com/cyberjso/sandbox/tree/master/apache-storm/word-counter), but this time using the DRPC storm support.
This feature is available on storm since too long. It basically let the consumers connect remotely on the cluster to make calls and then wait until a reply comes back.
This is how it works:

![drpc-workflow](https://raw.githubusercontent.com/cyberjso/sandbox/master/apache-storm/drpc-word-counter/drpc-workflow.png)


This feature is currently deprecated on storm-core. The documentation recommends to use [Apache Trident](https://storm.apache.org/documentation/Trident-API-Overview.html) in order to have the job done. 
This framework was built to attend other scenarios also (not only for RPC) as documentation suggests. A clear advantage of using It on this scenario is the implementation simplicity. Trident provides series of functions and utility classes that helps build usefulll stream pipelines. 
The same pipeline built by creating several classes was replaced by a singe method using Trident.


## Building 
mvn clean install

## Running
java -jar drpc-word-counter-1.0-SNAPSHOT-jar-with-dependencies.jar
