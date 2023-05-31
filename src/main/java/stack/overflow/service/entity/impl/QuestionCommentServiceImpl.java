package stack.overflow.service.entity.impl;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import stack.overflow.exception.AccessIsDeniedException;
import stack.overflow.model.entity.QuestionComment;
import stack.overflow.repository.entity.QuestionCommentRepository;
import stack.overflow.service.crud.impl.CrudServiceImpl;
import stack.overflow.service.dto.AccountResponseDtoService;
import stack.overflow.service.entity.QuestionCommentService;
import stack.overflow.util.AuthenticationProcessor;

@Service
public class QuestionCommentServiceImpl extends CrudServiceImpl<QuestionComment, Long> implements QuestionCommentService {
    private final QuestionCommentRepository repository;
    private final AccountResponseDtoService accountResponseDtoService;


    public QuestionCommentServiceImpl(QuestionCommentRepository repository, AccountResponseDtoService accountResponseDtoService) {
        super(repository);
        this.repository = repository;
        this.accountResponseDtoService = accountResponseDtoService;
    }

    @Override
    public void deleteByIdIfOwner(Long id, Authentication authentication) {
        if (isOwner(id, authentication)) {
            repository.deleteById(id);
        } else {
            throw new AccessIsDeniedException("The user is not authorized to delete the comment");
        }
    }

    private boolean isOwner(Long id, Authentication authentication) {
        String commentOwnerUsername = accountResponseDtoService.getByQuestionCommentId(id).getUsername();
        String loggedUserUsername = AuthenticationProcessor.extractUsername(authentication);
        return commentOwnerUsername.equals(loggedUserUsername);
    }
}
