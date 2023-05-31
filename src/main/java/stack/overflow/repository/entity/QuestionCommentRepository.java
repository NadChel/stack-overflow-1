package stack.overflow.repository.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import stack.overflow.model.entity.QuestionComment;

public interface QuestionCommentRepository  extends JpaRepository<QuestionComment, Long> {
}
