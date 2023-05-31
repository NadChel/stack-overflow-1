package stack.overflow.controller.expectationTesters;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.mock.web.MockHttpServletResponse;
import stack.overflow.model.api.Data;
import stack.overflow.model.dto.response.AccountResponseDto;
import stack.overflow.model.dto.response.QuestionCommentResponseDto;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GetCommentExpectationTester extends AbstractGetCommentExpectationTester {
    public GetCommentExpectationTester(Builder builder) {
        super(builder);
    }
    @Override
    public void test() throws JsonProcessingException {
        Data<QuestionCommentResponseDto> deserializedResponseBody =
                objectMapper.readValue(serializedResponseBody, Data.class);
        assertNotNull(deserializedResponseBody.getData());
        QuestionCommentResponseDto dto = deserializedResponseBody.getData();
        assertEquals(expectedQuestionId, dto.getQuestionId());
        assertEquals(expectedCommentText, dto.getText());
        assertNotNull(dto.getOwner());

        AccountResponseDto owner = dto.getOwner();
        assertEquals(expectedOwnerId, owner.getId());
        assertEquals(expectedOwnerUsername, owner.getUsername());
    }
    public static class Builder extends AbstractGetCommentExpectationTester.Builder {
        public Builder(MockHttpServletResponse response) throws UnsupportedEncodingException {
            super(response);
        }
        @Override
        public GetCommentExpectationTester build() {
            return new GetCommentExpectationTester(this);
        }
    }
}
