package io.example.hystrix;

import com.google.common.collect.Lists;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DNU {
    private FakeService fakeService = mock(FakeService.class);
    private static final Logger logger = Logger.getLogger(DNU.class.getName());
    private static final List<Integer> timeoutList = Lists.newArrayList(1000, 2000, 5000, 10000, 20000);
    private Random randomizer;

    public DNU(Random random) {
        this.randomizer = random;
    }

    public void start() {
        when(fakeService.doSomething()).thenAnswer(new Answer<String>() {
            public String answer(InvocationOnMock invocation) throws Throwable {
                Thread.sleep(getTimeout());
                return "succcess";
            }
        });
        new FakeServiceWrapper(fakeService).execute();
    }

    private Integer getTimeout() {
        Integer timeout = timeoutList.get(randomizer.nextInt(timeoutList.size()));
        logger.info("simulated timeout for this call is gonna be: " + timeout);
        return timeout;
    }

}
