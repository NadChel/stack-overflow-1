package stack.overflow.service.dto;

import stack.overflow.model.dto.response.AccountResponseDto;

import java.util.List;
import java.util.Map;

public interface AccountResponseDtoService {

    AccountResponseDto getOwnerByQuestionId(Long questionId);

    Map<Long, AccountResponseDto> getOwnersByQuestionIds(List<Long> questionIds);
}
