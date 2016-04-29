package io.example.hystrix;

import java.util.Random;

public class Main {
    private static Random random = new Random();

    public static void main(String[] args) {
        new DNU(random).start();
        new DNU(random).start();
        new DNU(random).start();
        new DNU(random).start();
        new DNU(random).start();
    }

}
