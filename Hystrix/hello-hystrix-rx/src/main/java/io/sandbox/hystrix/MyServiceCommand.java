package io.sandbox.hystrix;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixObservableCommand;
import rx.Observable;
import rx.Subscriber;

public class MyServiceCommand extends HystrixObservableCommand<MyOutput> {
    private MyService service;

    public MyServiceCommand() {
        super(Setter.withGroupKey(
                HystrixCommandGroupKey.Factory.asKey("myService"))
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter()
                                .withExecutionTimeoutInMilliseconds(1000)
                                .withCircuitBreakerErrorThresholdPercentage(2)));



        this.service = new MyService();
    }

    public Observable<MyOutput> resumeWithFallback() {
       return  Observable.create(new Observable.OnSubscribe<MyOutput>() {

           public void call(Subscriber<? super MyOutput> subscriber) {
               subscriber.onNext(new MyOutput());
               subscriber.onCompleted();
           }

       });
    }

    protected Observable<MyOutput> construct() {
        return Observable.create(new Observable.OnSubscribe<MyOutput>() {

            public void call(Subscriber<? super MyOutput> observer) {
                    observer.onNext(service.doSomething());
                    observer.onCompleted();
        }});

    }

}
