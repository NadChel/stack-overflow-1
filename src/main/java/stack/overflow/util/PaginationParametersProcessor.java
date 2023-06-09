package stack.overflow.util;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import stack.overflow.model.pagination.PaginationParameters;

import java.util.Optional;

/**
 * An object that provides shorthand methods for common operations
 * with {@code stack.overflow.model.pagination.PaginationParameters} instances
 */
public class PaginationParametersProcessor {
    /**
     * Extracts the sorting query modifier, such as "id" or "createdDate DESC",
     * associated with the given {@code PaginationParameters} instance, removing
     * any trailing whitespace
     *
     * @param params {@code PaginationParameters} instance
     * @return sorting modifier
     */
    public static String extractSortingModifierTrimmed(PaginationParameters params) {
        return params.sortType().getQuery().trim();
    }

    /**
     * Extracts the columns by which sorting should take place associated with
     * the given {@code PaginationParameters} instance
     *
     * @param params {@code PaginationParameters} instance
     * @return column name
     * @implNote invokes {@code extractSortingModifierTrimmed()} to get the associated
     * sorting modifier
     */
    public static String extractOrderByColumn(PaginationParameters params) {
        String sortingModifier = extractSortingModifierTrimmed(params);
        return (sortingModifier.indexOf(" ") > 0) ?
                sortingModifier.substring(0, sortingModifier.indexOf(" ")) :
                sortingModifier;
    }

    /**
     * Returns {@code true} if the associated sorting modifier mandates a descending order
     *
     * @param params {@code PaginationParameters} instance
     * @return {@code true} if the sorting order is descending, {@code false} otherwise
     * @implNote invokes {@code extractSortingModifierTrimmed()} to get the associated
     * sorting modifier
     */
    public static boolean isDescending(PaginationParameters params) {
        return extractSortingModifierTrimmed(params).contains("DESC");
    }

    /**
     * Extracts the page number associated with the given {@code PaginationParameters} instance
     * @param params {@code PaginationParameters} instance
     * @return an associated page number
     */
    public static int extractPageNumber(PaginationParameters params) {
        return params.pageNumber();
    }

    /**
     * Extracts the page size associated with the given {@code PaginationParameters} instance (corresponds to
     * a {@code LIMIT} clause in SQL)
     * @param params {@code PaginationParameters} instance
     * @return an associated page size
     */
    public static int extractPageSize(PaginationParameters params) {
        return params.pageSize();
    }

    /**
     * Extracts the index of the first result based on the associated page number
     * and page pageSize (corresponds to an {@code OFFSET} clause in SQL)
     *
     * @param params {@code PaginationParameters} instance
     * @implNote internally invokes extractPageNumber() and extractPageSize() to get the respective
     * values
     * @return an associated OFFSET value
     */
    public static int extractFirstResultIndex(PaginationParameters params) {
        return (extractPageNumber(params) - 1) * extractPageSize(params);
    }

    /**
     * Returns a {@code java.util.Optional} object that may contain search text associated
     * with the given {@code PaginationParameters} instance
     *
     * @param params {@code PaginationParameters} instance
     * @return an Optional instance that may contain an associated text String
     */
    public static Optional<String> extractText(PaginationParameters params) {
        return Optional.ofNullable(params.text());
    }

    /**
     * Returns a {@code PaginationParametersProcessingResult} object
     * @param params {@code PaginationParameters} instance
     * @implNote invokes {@code PaginationParametersProcessor}'s all public single-purpose methods
     * to pass their return values to the {@code PaginationParametersProcessingResult}'s constructor
     * @return {@code PaginationParametersProcessingResult} instance
     */
    public static PaginationParametersProcessingResult process(PaginationParameters params) {
        return new PaginationParametersProcessingResult(
                extractPageNumber(params),
                extractPageSize(params),
                extractFirstResultIndex(params),
                extractSortingModifierTrimmed(params),
                extractOrderByColumn(params),
                isDescending(params),
                extractText(params)
        );
    }

    /**
     * An object representing the results of calling each of {@code PaginationParametersProcessor}'s methods on
     * a given {@code PaginationParameters} instance
     */
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class PaginationParametersProcessingResult {
        private final int pageNumber;
        private final int pageSize;
        private final int firstResultIndex;
        private final String sortingModifier;
        private final String orderByColumn;
        private final boolean isDescending;
        private final Optional<String> text;

        public int pageNumber() {
            return this.pageNumber;
        }

        public int pageSize() {
            return this.pageSize;
        }

        public int firstResultIndex() {
            return this.firstResultIndex;
        }

        public String sortingModifier() {
            return this.sortingModifier;
        }

        public String orderByColumn() {
            return this.orderByColumn;
        }

        public boolean isDescending() {
            return this.isDescending;
        }

        public Optional<String> text() {
            return this.text;
        }
    }
}
