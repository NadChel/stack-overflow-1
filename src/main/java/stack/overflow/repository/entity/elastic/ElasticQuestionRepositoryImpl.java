package stack.overflow.repository.entity.elastic;

import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Repository;
import stack.overflow.model.dto.response.QuestionResponseDto;
import stack.overflow.model.entity.elastic.ElasticQuestion;
import stack.overflow.model.mapper.ElasticQuestionMapper;

@Repository
@RequiredArgsConstructor
public class ElasticQuestionRepositoryImpl implements ElasticQuestionRepository {
    private final ElasticsearchRestTemplate restTemplate;
    private final ElasticQuestionMapper mapper;

    @Override
    public QuestionResponseDto create(ElasticQuestion question) {
        ElasticQuestion elasticQuestion = restTemplate.save(question);
        return mapper.elasticToDto(elasticQuestion);
    }

    @Override
    public void delete(ElasticQuestion question) {
        restTemplate.delete(question);
    }
}
