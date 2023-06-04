package stack.overflow.repository.pagination;

import stack.overflow.model.pagination.PaginationParameters;

import java.util.List;

public interface PaginationRepository<T> {

    List<T> getDtosWithoutSetOwner(PaginationParameters paginationParameters);

    Long getCount();
}
