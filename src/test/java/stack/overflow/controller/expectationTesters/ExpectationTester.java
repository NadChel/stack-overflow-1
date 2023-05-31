package stack.overflow.controller.expectationTesters;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface ExpectationTester {
    void test() throws JsonProcessingException;
}
