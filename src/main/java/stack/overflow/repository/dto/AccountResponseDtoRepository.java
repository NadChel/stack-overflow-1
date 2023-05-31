package stack.overflow.repository.dto;

import stack.overflow.model.dto.response.AccountResponseDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AccountResponseDtoRepository {

    Optional<AccountResponseDto> getOwnerByQuestionId(Long questionId);

    Map<Long, AccountResponseDto> getOwnersByQuestionIds(List<Long> questionIds);

    Optional<AccountResponseDto> getByQuestionCommentId(Long id);
}
