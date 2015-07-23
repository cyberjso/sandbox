# Word Counter - DRPC Version

This is the same problem described that was already solved here, but this time using the distributed remote procedure call available on storm.
This feature is available on storm since too long. It basically lets the consumers connect remotly on the cluster to make calls and then wait until a reply comes back.

This feature is officially deprecated, but now is provided by Trident. There are other reasons I'd recommend use Trident definitly. I'm using Trident for this case
just because the DRPC feature was officially shifted to there. 

An outcome of this approach is the implementation simplicity. Trident provides series of functions and utility classes that helps build usefulll stream pipelines. 
The same pipeline built by creating several classes was replaced by a singe method using Trident.


## Building 
mvn clean install

## Running
java -jar target\apache-storm-sandbox-1.0-SNAPSHOT-jar-with-dependencies.jar default-input.txt

## Ouput
word1 -- :  -- 4
word0 -- :  -- 1
word3 -- :  -- 3
word2 -- :  -- 2
word5 -- :  -- 1
word4 -- :  -- 1