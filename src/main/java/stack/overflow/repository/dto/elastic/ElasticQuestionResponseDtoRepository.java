package stack.overflow.repository.dto.elastic;

import stack.overflow.model.dto.response.QuestionResponseDto;
import stack.overflow.model.pagination.PaginationParameters;

import java.util.List;

public interface ElasticQuestionResponseDtoRepository {
    List<QuestionResponseDto> getDtos(PaginationParameters params);
    Long getCount();
}
