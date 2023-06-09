package stack.overflow.service.dto.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import stack.overflow.exception.EntityNotFoundException;
import stack.overflow.model.dto.response.QuestionResponseDto;
import stack.overflow.model.pagination.Page;
import stack.overflow.model.pagination.PaginationParameters;
import stack.overflow.repository.dto.QuestionResponseDtoRepository;
import stack.overflow.repository.dto.elastic.ElasticQuestionResponseDtoRepository;
import stack.overflow.service.dto.AccountResponseDtoService;
import stack.overflow.service.dto.QuestionResponseDtoService;
import stack.overflow.service.dto.TagResponseDtoService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QuestionResponseDtoServiceImpl implements QuestionResponseDtoService {

    private final QuestionResponseDtoRepository questionResponseDtoRepository;
    private final AccountResponseDtoService accountResponseDtoService;
    private final TagResponseDtoService tagResponseDtoService;
    private final ElasticQuestionResponseDtoRepository elasticQuestionRepository;

    @Override
    public QuestionResponseDto getByQuestionId(Long questionId) {
        QuestionResponseDto resultDto = questionResponseDtoRepository.getByQuestionId(questionId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Question with id#%d not found", questionId)));
        resultDto.setOwner(accountResponseDtoService.getOwnerByQuestionId(questionId));
        resultDto.setTags(tagResponseDtoService.getByQuestionId(questionId));
        return resultDto;
    }

    @Override
    public Page<QuestionResponseDto> getPage(PaginationParameters paginationParameters) {
        List<QuestionResponseDto> resultDtos = elasticQuestionRepository.getDtos(paginationParameters);
        Long resultCount = elasticQuestionRepository.getCount();
        return new Page<>(resultDtos, resultCount);
    }
}
