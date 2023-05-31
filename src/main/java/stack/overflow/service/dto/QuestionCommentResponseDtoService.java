package stack.overflow.service.dto;

import stack.overflow.model.dto.response.QuestionCommentResponseDto;
import stack.overflow.model.pagination.Page;
import stack.overflow.model.pagination.PaginationParameters;

public interface QuestionCommentResponseDtoService {
    QuestionCommentResponseDto getById(Long id);

    Page<QuestionCommentResponseDto> getPage(PaginationParameters params);
}
