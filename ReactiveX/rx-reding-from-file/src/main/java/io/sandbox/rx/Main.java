package io.sandbox.rx;

import io.sandbox.rx.naosei.DbDumpParser;

public class Main {

    public static void main(String[] args) {

        new DbDumpParser().process("db-dump.txt");

    }

}
