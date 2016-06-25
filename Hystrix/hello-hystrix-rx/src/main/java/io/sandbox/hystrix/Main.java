package io.sandbox.hystrix;

import rx.functions.Action1;

import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws Exception {
        IntStream.rangeClosed(1, 500).forEach(i ->
                new MyServiceCommand()
                        .observe()
                        .subscribe(new Action1<MyOutput>() {

                            public void call(MyOutput myOutput) {
                                if (myOutput.getId() == null) System.out.println("Fallback algorithm in action");
                                else System.out.println(myOutput.toString());
                            }
                        }));
    }

}
