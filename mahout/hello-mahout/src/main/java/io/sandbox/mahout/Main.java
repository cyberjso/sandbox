package io.sandbox.mahout;

public class Main {

    public static void main(String[] args) {
        new BasisUserBasedRecommender().recommend(1, 2).stream().forEach(System.out::println);
    }

}
