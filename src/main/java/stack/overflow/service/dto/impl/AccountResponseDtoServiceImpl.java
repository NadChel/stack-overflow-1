package stack.overflow.service.dto.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import stack.overflow.exception.EntityNotFoundException;
import stack.overflow.model.dto.response.AccountResponseDto;
import stack.overflow.repository.dto.AccountResponseDtoRepository;
import stack.overflow.service.dto.AccountResponseDtoService;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class AccountResponseDtoServiceImpl implements AccountResponseDtoService {

    private final AccountResponseDtoRepository accountResponseDtoRepository;

    @Override
    public AccountResponseDto getOwnerByQuestionId(Long questionId) {
        return accountResponseDtoRepository.getOwnerByQuestionId(questionId).orElseThrow(() ->
                new EntityNotFoundException(String.format("The owner of the question with id#%d not found", questionId)));
    }

    @Override
    public Map<Long, AccountResponseDto> getOwnersByQuestionIds(List<Long> questionIds) {
        return accountResponseDtoRepository.getOwnersByQuestionIds(questionIds);
    }

    @Override
    public AccountResponseDto getByQuestionCommentId(Long questionCommentId) {
        return accountResponseDtoRepository.getByQuestionCommentId(questionCommentId).orElseThrow(() ->
                new EntityNotFoundException(String.format("The owner of the question comment with id#%d not found",
                        questionCommentId)));
    }
}
