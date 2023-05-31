package stack.overflow.model.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.security.core.Authentication;
import stack.overflow.model.dto.request.QuestionCommentRequestDto;
import stack.overflow.model.entity.Account;
import stack.overflow.model.entity.QuestionComment;
import stack.overflow.util.AuthenticationProcessor;

@Mapper
public abstract class QuestionCommentMapper {
    @AfterMapping
    protected void enrichWithOwner(Authentication auth, @MappingTarget QuestionComment questionComment) {
        Account owner = AuthenticationProcessor.extractAccount(auth);
        questionComment.setOwner(owner);
    }

    public abstract QuestionComment dtoAndAuthenticationToQuestionComment(QuestionCommentRequestDto dto,
                                                                          @Context Authentication auth);
}
