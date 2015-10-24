package io.sandbox.mahout;

public class Main {

    public static void main(String[] args) {
        new BasisUserBasedRecommender().recommend("/user-choices.properties").stream().forEach(System.out::println);
    }

}
