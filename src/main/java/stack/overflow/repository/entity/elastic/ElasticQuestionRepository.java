package stack.overflow.repository.entity.elastic;

import stack.overflow.model.dto.response.QuestionResponseDto;
import stack.overflow.model.entity.elastic.ElasticQuestion;
import stack.overflow.model.pagination.PaginationParameters;

import java.util.List;

public interface ElasticQuestionRepository {
    QuestionResponseDto create(ElasticQuestion question);

    void delete(ElasticQuestion question);

    List<QuestionResponseDto> getDtos(PaginationParameters params);

    long getCount(PaginationParameters params);
}
