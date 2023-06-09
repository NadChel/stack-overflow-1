package stack.overflow.repository.entity.elastic;

import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;
import stack.overflow.model.dto.response.QuestionResponseDto;
import stack.overflow.model.entity.elastic.ElasticQuestion;
import stack.overflow.model.mapper.ElasticQuestionMapper;
import stack.overflow.model.pagination.PaginationParameters;
import stack.overflow.util.PaginationParametersProcessor;

import java.util.List;

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

    @Override
    public List<QuestionResponseDto> getDtos(PaginationParameters params) {
        var p = PaginationParametersProcessor.process(params);
        SortOrder sortOrder = p.isDescending() ?
                SortOrder.DESC : SortOrder.ASC;

        var queryBuilder = new NativeSearchQueryBuilder()
                .withSorts(SortBuilders.fieldSort(p.orderByColumn())
                        .order(sortOrder))
                .withPageable(Pageable.ofSize(p.pageSize()).withPage(p.pageNumber()));

        if (p.text().isPresent()) {
            queryBuilder = queryBuilder.withQuery(QueryBuilders.multiMatchQuery(p.text()));
        }

        Query query = queryBuilder.build();

        SearchHits<ElasticQuestion> searchHits = restTemplate.search(query, ElasticQuestion.class);

        return searchHits.get()
                .map(SearchHit::getContent)
                .map(mapper::elasticToDto)
                .toList();
    }

    @Override
    public long getCount(PaginationParameters params) {
        Query query = new NativeSearchQueryBuilder().build();
        return restTemplate.count(query, ElasticQuestion.class);
    }
}
