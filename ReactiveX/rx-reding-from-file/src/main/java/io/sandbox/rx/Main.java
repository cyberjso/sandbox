package io.sandbox.rx;

import io.sandbox.rx.fileparser.DbDumpParser;

public class Main {

    public static void main(String[] args) {

        new DbDumpParser().process("input.txt");

    }

}
