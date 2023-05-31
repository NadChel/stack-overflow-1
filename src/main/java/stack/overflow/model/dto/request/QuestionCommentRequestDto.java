package stack.overflow.model.dto.request;

import javax.validation.constraints.NotBlank;

public record QuestionCommentRequestDto(@NotBlank String text, @NotBlank Long questionId) {}