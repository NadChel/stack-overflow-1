package stack.overflow.model.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;
@Getter
public class QuestionCommentResponseDto {
    private final Long id;
    private final Long questionId;
    private final LocalDateTime createdDate;
    private final LocalDateTime modifiedDate;
    private final String text;
    @Setter
    private AccountResponseDto owner;

    public QuestionCommentResponseDto(Long id, Long questionId, LocalDateTime createdDate,
                                      LocalDateTime modifiedDate, String text) {
        this.id = id;
        this.questionId = questionId;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.text = text;
    }
    @JsonCreator
    private QuestionCommentResponseDto(Long id, Long questionId,
                                       LocalDateTime createdDate,
                                       LocalDateTime modifiedDate,
                                       String text, AccountResponseDto owner) {
        this(id, questionId, createdDate, modifiedDate, text);
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionCommentResponseDto that = (QuestionCommentResponseDto) o;
        return id.equals(that.id) && questionId.equals(that.questionId) && Objects.equals(createdDate, that.createdDate) && Objects.equals(modifiedDate, that.modifiedDate) && Objects.equals(text, that.text) && Objects.equals(owner, that.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, questionId, createdDate, modifiedDate, text, owner);
    }
}