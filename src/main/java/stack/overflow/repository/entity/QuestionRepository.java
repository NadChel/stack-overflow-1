package stack.overflow.repository.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import stack.overflow.model.entity.Question;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("""
            SELECT q
            FROM Question q
            JOIN FETCH q.owner ow
            WHERE q.id = :questionId
            """)
    Optional<Question> getByQuestionIdWithOwner(Long questionId);
}
