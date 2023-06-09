package stack.overflow.model.mapper;

import org.mapstruct.Mapper;
import stack.overflow.model.dto.response.QuestionResponseDto;
import stack.overflow.model.entity.Question;
import stack.overflow.model.entity.elastic.ElasticQuestion;

@Mapper(componentModel = "spring")
public interface ElasticQuestionMapper {
    ElasticQuestion elasticize(Question question);
    QuestionResponseDto elasticToDto(ElasticQuestion elasticQuestion);
}
