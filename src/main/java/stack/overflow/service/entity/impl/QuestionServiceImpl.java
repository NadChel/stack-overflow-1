package stack.overflow.service.entity.impl;

import org.springframework.stereotype.Service;
import stack.overflow.exception.EntityNotFoundException;
import stack.overflow.model.entity.Question;
import stack.overflow.model.entity.elastic.ElasticQuestion;
import stack.overflow.model.mapper.ElasticQuestionMapper;
import stack.overflow.repository.entity.QuestionRepository;
import stack.overflow.repository.entity.elastic.ElasticQuestionRepository;
import stack.overflow.service.crud.impl.CrudServiceImpl;
import stack.overflow.service.entity.QuestionService;

@Service
public class QuestionServiceImpl extends CrudServiceImpl<Question, Long> implements QuestionService {

    private final QuestionRepository questionRepository;
    private final ElasticQuestionRepository elasticQuestionRepository;
    private final ElasticQuestionMapper mapper;

    public QuestionServiceImpl(QuestionRepository questionRepository, ElasticQuestionRepository elasticQuestionRepository, ElasticQuestionMapper mapper) {
        super(questionRepository);
        this.questionRepository = questionRepository;
        this.elasticQuestionRepository = elasticQuestionRepository;
        this.mapper = mapper;
    }

    @Override
    public Question create(Question question) {
        ElasticQuestion elasticQuestion = mapper.elasticize(question);
        elasticQuestionRepository.create(elasticQuestion);
        return super.create(question);
    }

    @Override
    public void delete(Question question) {
        ElasticQuestion elasticQuestion = mapper.elasticize(question);
        elasticQuestionRepository.delete(elasticQuestion);
        super.delete(question);
    }

    @Override
    public Question getByQuestionIdWithOwner(Long questionId) {
        return questionRepository.getByQuestionIdWithOwner(questionId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Question with id#%d not found", questionId)));
    }
}
