package stack.overflow.repository.dto.impl;

import org.springframework.stereotype.Repository;
import stack.overflow.model.dto.response.QuestionCommentResponseDto;
import stack.overflow.model.pagination.PaginationParameters;
import stack.overflow.repository.dto.QuestionCommentResponseDtoRepository;
import stack.overflow.util.JpaResultUtil;
import stack.overflow.util.PaginationParametersProcessor;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class QuestionCommentResponseDtoRepositoryImpl implements QuestionCommentResponseDtoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<QuestionCommentResponseDto> getByIdWithoutSetOwner(Long id) {
        TypedQuery<QuestionCommentResponseDto> query = entityManager.createQuery("""
                            SELECT new stack.overflow.model.dto.response.QuestionCommentResponseDto (
                            qc.id, q.id, qc.createdDate, qc.modifiedDate, qc.text
                            ) FROM QuestionComment qc JOIN FETCH Question q
                            WHERE qc.id = :id
                        """, QuestionCommentResponseDto.class)
                .setParameter("id", id);
        return JpaResultUtil.getSingleResultOrNull(query);
    }

    @Override
    public List<QuestionCommentResponseDto> getDtos(PaginationParameters params) {
        String sortingModifier = PaginationParametersProcessor.extractQuery(params);
        int offset = PaginationParametersProcessor.extractFirstResultIndex(params);
        return entityManager.createQuery("""
                SELECT new stack.overflow.model.dto.response.QuestionCommentResponseDto (
                            qc.id, q.id, qc.createdDate, qc.modifiedDate, qc.text
                ) FROM QuestionComment qc JOIN FETCH Question q
                ORDER BY :sort
                """, QuestionCommentResponseDto.class)
                .setParameter("sort", sortingModifier)
                .setFirstResult(offset)
                .getResultList();
    }

    @Override
    public Long getCount() {
        return entityManager.createQuery("""
                        SELECT COUNT(qc.id)
                        FROM QuestionComment qc
                        """, Long.class)
                .getSingleResult();
    }
}
