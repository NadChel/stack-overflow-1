package stack.overflow.model.pagination;

import stack.overflow.model.enumeration.SortType;

public record PaginationParameters(
        Integer pageNumber, Integer pageSize, SortType sortType, String text
) {

    public static PaginationParametersBuilder builder(int pageNumber) {
        return new PaginationParametersBuilder(pageNumber);
    }

    public static class PaginationParametersBuilder {
        private final Integer pageNumber;
        private Integer pageSize;
        private SortType sortType;
        private String text;

        PaginationParametersBuilder(int pageNumber) {
            this.pageNumber = pageNumber;
        }

        public PaginationParametersBuilder withPageSize(Integer pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public PaginationParametersBuilder withSortType(SortType sortType) {
            this.sortType = sortType;
            return this;
        }

        public PaginationParametersBuilder withText(String text) {
            this.text = text;
            return this;
        }

        public PaginationParameters build() {
            return new PaginationParameters(pageNumber, pageSize, sortType, text);
        }

        public String toString() {
            return "PaginationParameters.PaginationParametersBuilder(pageNumber=" + this.pageNumber + ", pageSize=" + this.pageSize + ", sortType=" + this.sortType + ", text=" + this.text + ")";
        }
    }
}
