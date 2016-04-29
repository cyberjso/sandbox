package io.sandbox.nonblockingio;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public class Main {
    private List<String> files = Arrays.asList("file-3.txt", "file-1.txt", "file-2.txt");
    private static final Logger logger = LogManager.getLogger(Reader.class);

    public static  void main(String[] args) {
        new Read().read();
    }

}
