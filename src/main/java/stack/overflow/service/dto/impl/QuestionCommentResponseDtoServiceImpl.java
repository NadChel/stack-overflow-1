package stack.overflow.service.dto.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import stack.overflow.exception.EntityNotFoundException;
import stack.overflow.model.dto.response.QuestionCommentResponseDto;
import stack.overflow.model.pagination.Page;
import stack.overflow.model.pagination.PaginationParameters;
import stack.overflow.repository.dto.QuestionCommentResponseDtoRepository;
import stack.overflow.service.dto.AccountResponseDtoService;
import stack.overflow.service.dto.QuestionCommentResponseDtoService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QuestionCommentResponseDtoServiceImpl implements QuestionCommentResponseDtoService {
    private final QuestionCommentResponseDtoRepository repository;
    private final AccountResponseDtoService accountService;

    @Override
    public QuestionCommentResponseDto getById(Long id) {
        QuestionCommentResponseDto questionCommentResponseDto = repository.getByIdWithoutSetOwner(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Question comment with id#%d not found", id)));
        questionCommentResponseDto.setOwner(accountService.getByQuestionCommentId(id));
        return questionCommentResponseDto;
    }

    @Override
    public Page<QuestionCommentResponseDto> getPage(PaginationParameters params) {
        List<QuestionCommentResponseDto> dtos = repository.getDtos(params);
        Long count = repository.getCount();
        return new Page<>(dtos, count);
    }
}
