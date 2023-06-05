package stack.overflow.util;

import stack.overflow.model.pagination.PaginationParameters;

import javax.persistence.Entity;

public class PaginationParametersProcessor {
    public static String extractSortingModifier(PaginationParameters params) {
        return params.sortType().getQuery();
    }

    public static String extractSortingModifier(PaginationParameters params,
                                                Class<?> entity) {
        String nonadaptedQuery = extractSortingModifier(params);
        String entityTableName = extractEntityTableName(entity);
        String adaptedQuery = nonadaptedQuery.replaceFirst("(?=\\w)", entityTableName + ".");
        return adaptedQuery;
    }

    private static String extractEntityTableName(Class<?> entity) {
        if (entity.isAnnotationPresent(Entity.class)) {
            return entity.getSimpleName();
        } else {
            throw new IllegalArgumentException(String.format("%s is not an @Entity", entity.getName()));
        }
    }

    public static int extractFirstResultIndex(PaginationParameters params) {
        return (params.pageNumber() - 1) * params.size();
    }

    public static int extractMaxResults(PaginationParameters params) {
        return params.size();
    }
}
