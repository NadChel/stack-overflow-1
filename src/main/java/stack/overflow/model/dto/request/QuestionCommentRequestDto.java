package stack.overflow.model.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record QuestionCommentRequestDto(
        @NotBlank String text,
        @NotNull Long questionId) {}