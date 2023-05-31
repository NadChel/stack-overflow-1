package stack.overflow.service.entity;

import org.springframework.security.core.Authentication;
import stack.overflow.model.entity.QuestionComment;
import stack.overflow.service.crud.CrudService;

public interface QuestionCommentService extends CrudService<QuestionComment, Long> {
    void deleteByIdIfOwner(Long id, Authentication authentication);
}
