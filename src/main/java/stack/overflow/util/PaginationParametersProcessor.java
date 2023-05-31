package stack.overflow.util;

import stack.overflow.model.pagination.PaginationParameters;

public class PaginationParametersProcessor {
    public static String extractQuery(PaginationParameters params) {
        return params.sortType().getQuery();
    }

    public static int extractFirstResultIndex(PaginationParameters params) {
        return (params.pageNumber() - 1) * params.size();
    }
}
