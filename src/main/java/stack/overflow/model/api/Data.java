package stack.overflow.model.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public final class Data<T> {

    private final T data;

    private Data(T data) {
        this.data = data;
    }
    @JsonCreator
    public static <T> Data<T> build(T data) {
        return new Data<>(data);
    }
}
