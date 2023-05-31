package stack.overflow.repository.dto;

import stack.overflow.model.dto.response.QuestionCommentResponseDto;
import stack.overflow.repository.pagination.PaginationRepository;

import java.util.Optional;

public interface QuestionCommentResponseDtoRepository extends PaginationRepository<QuestionCommentResponseDto> {
    Optional<QuestionCommentResponseDto> getByIdWithoutSetOwner(Long questionCommentId);
}
