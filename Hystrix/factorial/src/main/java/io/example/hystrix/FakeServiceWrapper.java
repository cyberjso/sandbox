package io.example.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

import java.util.logging.Logger;

public class FakeServiceWrapper extends HystrixCommand<Foo> {
    private static final Logger logger = Logger.getLogger(FakeServiceWrapper.class.getName());
    private FakeService externalService;

    public FakeServiceWrapper(FakeService service)  {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("WebService"))
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(3000)));
        externalService = service;
    }

    @Override
    public Foo run()  {
        logger.info("calling fakeService");
        return new Foo(externalService.doSomething());
    }

    @Override
    public Foo getFallback(){
        logger.info("*** falling back ****");
        return new Foo("timeout -> come back later");
    }


}
