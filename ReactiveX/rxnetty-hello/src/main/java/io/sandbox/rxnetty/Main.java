package io.sandbox.rxnetty;

public class Main {

    public static void main(String[] args) {
        new HelloResource().buildHello().startAndWait();
    }

}
