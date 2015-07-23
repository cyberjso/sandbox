# Word Counter

Streams a given text file content to a implementation that using apache-storm counts the occurrence of each word inside.

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