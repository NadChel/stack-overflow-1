package stack.overflow.util;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Optional;

public final class JpaResultUtil {

    private JpaResultUtil() {
    }

    public static <T> Optional<T> getSingleResultOrNull(TypedQuery<T> query) {
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
