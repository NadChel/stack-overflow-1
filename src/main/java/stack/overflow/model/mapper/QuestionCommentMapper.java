package stack.overflow.model.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import stack.overflow.model.dto.request.QuestionCommentRequestDto;
import stack.overflow.model.entity.Account;
import stack.overflow.model.entity.Question;
import stack.overflow.model.entity.QuestionComment;
import stack.overflow.service.entity.QuestionService;
import stack.overflow.util.AuthenticationProcessor;

@Mapper(componentModel = "spring")
public abstract class QuestionCommentMapper {
    @Autowired
    protected QuestionService questionService;

    public abstract QuestionComment dtoAndAuthenticationToQuestionComment(QuestionCommentRequestDto dto,
                                                                          @Context Authentication auth);

    @AfterMapping
    protected void enrichWithOwner(@MappingTarget QuestionComment questionComment,
                                   @Context Authentication auth) {
        Account owner = AuthenticationProcessor.extractAccount(auth);
        questionComment.setOwner(owner);
    }

    @AfterMapping
    protected void enrichWithQuestion(@MappingTarget QuestionComment questionComment,
                                      QuestionCommentRequestDto dto) {
        Long questionId = dto.questionId();
        Question question = questionService.getById(questionId);
        questionComment.setQuestion(question);
    }
}
