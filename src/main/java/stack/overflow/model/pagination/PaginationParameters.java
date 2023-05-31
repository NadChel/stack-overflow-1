package stack.overflow.model.pagination;

import stack.overflow.model.enumeration.SortType;

public record PaginationParameters(
        Integer pageNumber, Integer size, SortType sortType
) {
    public static PaginationParameters ofPageNumberSizeAndSortType(Integer pageNumber, Integer pageSize,
                                                                   SortType sortType) {
        return new PaginationParameters(pageNumber, pageSize, sortType);
    }
}
