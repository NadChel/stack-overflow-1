package stack.overflow.repository.entity.elastic;

import stack.overflow.model.dto.response.QuestionResponseDto;
import stack.overflow.model.entity.elastic.ElasticQuestion;

public interface ElasticQuestionRepository {
    QuestionResponseDto create(ElasticQuestion question);

    void delete(ElasticQuestion question);
}
