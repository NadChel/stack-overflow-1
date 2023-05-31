package stack.overflow.repository.dto.impl;

import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;
import stack.overflow.model.dto.response.AccountResponseDto;
import stack.overflow.repository.dto.AccountResponseDtoRepository;
import stack.overflow.util.JpaResultUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class AccountResponseDtoRepositoryImpl implements AccountResponseDtoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<AccountResponseDto> getOwnerByQuestionId(Long questionId) {
        TypedQuery<AccountResponseDto> typedQuery = entityManager.createQuery(
                        """
                                SELECT new stack.overflow.model.dto.response.AccountResponseDto(
                                ow.id,
                                ow.username)
                                FROM Question q
                                JOIN q.owner ow
                                WHERE q.id = :questionId
                                """, AccountResponseDto.class)
                .setParameter("questionId", questionId);
        return JpaResultUtil.getSingleResultOrNull(typedQuery);
    }

    @Override
    public Map<Long, AccountResponseDto> getOwnersByQuestionIds(List<Long> questionIds) {
        Map<Long, AccountResponseDto> resultMap = new HashMap<>();
        entityManager.createQuery(
                        """
                                SELECT
                                q.id,
                                ow.id,
                                ow.username
                                FROM Question q
                                JOIN q.owner ow
                                WHERE q.id IN :questionIds
                                """, Tuple.class)
                .setParameter("questionIds", questionIds)
                .unwrap(Query.class)
                .setResultTransformer(new ResultTransformer() {

                    @Override
                    public Object transformTuple(Object[] objects, String[] strings) {
                        Long questionId = (Long) objects[0];
                        Long ownerId = (Long) objects[1];
                        String ownerUsername = (String) objects[2];
                        resultMap.put(questionId, new AccountResponseDto(ownerId, ownerUsername));
                        return null;
                    }

                    @Override
                    public List transformList(List list) {
                        return list;
                    }
                })
                .getResultList();
        return resultMap;
    }

    @Override
    public Optional<AccountResponseDto> getByQuestionCommentId(Long id) {
        TypedQuery<AccountResponseDto> query = entityManager.createQuery("""
                SELECT new stack.overflow.model.dto.response.AccountResponseDto(a.id, a.username)
                FROM Account a JOIN QuestionComment qc
                ON a.id = qc.owner.id
                WHERE qc.id = :id
            """, AccountResponseDto.class)
                .setParameter("id", id);
       return JpaResultUtil.getSingleResultOrNull(query);
    }
}
