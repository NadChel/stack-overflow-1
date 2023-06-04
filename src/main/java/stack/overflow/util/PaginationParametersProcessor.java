package stack.overflow.util;

import stack.overflow.model.pagination.PaginationParameters;

public class PaginationParametersProcessor {
    public static String extractSortingModifierTrimmed(PaginationParameters params) {
        return params.sortType().getQuery().trim();
    }

    public static String extractOrderByColumn(PaginationParameters params) {
        String sortingModifier = extractSortingModifierTrimmed(params);
        return (sortingModifier.indexOf(" ") > 0) ?
                sortingModifier.substring(0, sortingModifier.indexOf(" ")) :
                sortingModifier;
    }

    public static boolean isDescending(PaginationParameters params) {
        return extractSortingModifierTrimmed(params).contains("DESC");
    }

    public static int extractFirstResultIndex(PaginationParameters params) {
        return (params.pageNumber() - 1) * params.size();
    }

    public static int extractMaxResults(PaginationParameters params) {
        return params.size();
    }
}
