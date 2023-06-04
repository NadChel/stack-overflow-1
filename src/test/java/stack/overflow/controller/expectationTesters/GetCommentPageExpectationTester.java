package stack.overflow.controller.expectationTesters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.mock.web.MockHttpServletResponse;
import stack.overflow.model.api.Data;
import stack.overflow.model.dto.response.AccountResponseDto;
import stack.overflow.model.dto.response.QuestionCommentResponseDto;
import stack.overflow.model.pagination.Page;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GetCommentPageExpectationTester extends AbstractGetCommentExpectationTester {
    private final int expectedPageCount;
    private final int expectedPageDtoListSize;
    private final int expectedFirstCommentId;

    private GetCommentPageExpectationTester(Builder builder) {
        super(builder);
        this.expectedPageCount = builder.expectedPageCount;
        this.expectedPageDtoListSize = builder.expectedPageDtoListSize;
        this.expectedFirstCommentId = builder.expectedFirstCommentId;
    }
    @Override
    public void test() throws JsonProcessingException {
        TypeReference<Data<Page<QuestionCommentResponseDto>>> typeReference =
                new TypeReference<>() {};
        Data<Page<QuestionCommentResponseDto>> deserializedResponseBody =
                objectMapper.readValue(serializedResponseBody, typeReference);
        assertNotNull(deserializedResponseBody.getData());
        Page<QuestionCommentResponseDto> page = deserializedResponseBody.getData();
        assertEquals(expectedPageCount, page.getCount());
        assertNotNull(page.getDtos());
        List<QuestionCommentResponseDto> dtoList = page.getDtos();
        assertEquals(expectedPageDtoListSize, dtoList.size());

        QuestionCommentResponseDto firstDto = dtoList.get(0);
        assertEquals(expectedFirstCommentId, firstDto.getId());

        for (QuestionCommentResponseDto dto : dtoList) {
            assertEquals(expectedQuestionId, dto.getQuestionId());
            assertEquals(expectedCommentText, dto.getText());
            assertNotNull(dto.getCreatedDate());
            assertNotNull(dto.getModifiedDate());

            AccountResponseDto actualOwner = dto.getOwner();
            assertEquals(expectedOwnerId, actualOwner.getId());
            assertEquals(expectedOwnerUsername, actualOwner.getUsername());
        }
    }
    public static class Builder extends AbstractGetCommentExpectationTester.Builder {
        private int expectedPageCount = 10;
        private int expectedPageDtoListSize = 10;
        private int expectedFirstCommentId = 1;

        public Builder(MockHttpServletResponse response) throws UnsupportedEncodingException {
            super(response);
        }

        public Builder setExpectedPageCount(int expectedPageCount) {
            this.expectedPageCount = expectedPageCount;
            return this;
        }
        public Builder setExpectedPageDtoListSize(int expectedPageDtoListSize) {
            this.expectedPageDtoListSize = expectedPageDtoListSize;
            return this;
        }
        public Builder setExpectedFirstCommentId(int expectedFirstCommentId) {
            this.expectedFirstCommentId = expectedFirstCommentId;
            return this;
        }
        @Override
        public GetCommentPageExpectationTester build() {
            return new GetCommentPageExpectationTester(this);
        }
    }
}
