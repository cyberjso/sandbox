package io.example.hystrix;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class FakeServiceImpl implements Answer<String> {


    public String answer(InvocationOnMock invocation){
        return "Success";
    }
}
