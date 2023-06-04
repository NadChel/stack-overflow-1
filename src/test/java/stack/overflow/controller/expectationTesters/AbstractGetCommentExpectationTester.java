package stack.overflow.controller.expectationTesters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.UnsupportedEncodingException;

public abstract class AbstractGetCommentExpectationTester implements ExpectationTester {
    protected final String serializedResponseBody;
    protected final String expectedCommentText;
    protected final int expectedQuestionId;
    protected final int expectedOwnerId;
    protected final String expectedOwnerUsername;
    protected final ObjectMapper objectMapper;
    protected AbstractGetCommentExpectationTester(Builder builder) {
        this.serializedResponseBody = builder.serializedResponseBody;
        this.expectedOwnerId = builder.expectedOwnerId;
        this.expectedOwnerUsername = builder.expectedOwnerUsername;
        this.expectedQuestionId = builder.expectedQuestionId;
        this.expectedCommentText = builder.expectedCommentText;
        this.objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .registerModule(new ParameterNamesModule());
    }
    public abstract void test() throws JsonProcessingException;
    public abstract static class Builder {
        protected final String serializedResponseBody;
        protected String expectedCommentText = "text";
        protected int expectedQuestionId = 1;
        protected int expectedOwnerId = 1;
        protected String expectedOwnerUsername = "user";
        protected Builder(MockHttpServletResponse response) throws UnsupportedEncodingException {
            this.serializedResponseBody = response.getContentAsString();
        }
        public Builder setExpectedOwnerUsername(String expectedOwnerUsername) {
            this.expectedOwnerUsername = expectedOwnerUsername;
            return this;
        }
        public Builder setExpectedOwnerId(int expectedOwnerId) {
            this.expectedOwnerId = expectedOwnerId;
            return this;
        }
        public Builder setExpectedQuestionId(int expectedQuestionId) {
            this.expectedQuestionId = expectedQuestionId;
            return this;
        }
        public Builder setExpectedCommentText(String expectedCommentText) {
            this.expectedCommentText = expectedCommentText;
            return this;
        }
        public abstract AbstractGetCommentExpectationTester build();
    }
}
