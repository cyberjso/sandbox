package io.sandbox.hystrix;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class MyService {
    private Map<Integer, Service> impls;

    public MyService() {
        impls  = new HashMap<Integer, Service>();
        impls.put(0, () -> { return new MyOutput(UUID.randomUUID().toString(), "name"); });
        impls.put(1, () -> { throw  new RuntimeException("Some error"); });
        impls.put(2, () -> {
            System.out.println("It's gonna timeout");
            sleep(4000);
            return new MyOutput(UUID.randomUUID().toString(), "name");
        });
    }
    private void sleep(int seconds) {
        try {
            Thread.sleep(seconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public MyOutput doSomething() {
        return impls.get(new Random().nextInt(3)).doSomethihg();
    }
}
