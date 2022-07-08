package shop.spring.dev.springshop.api.response;

import lombok.Getter;

@Getter
public class SingleResult<T> extends CommonResult {
    private T data;

    public SingleResult(Boolean isSuccess, Integer statusCode, String message, T data) {
        super(isSuccess, statusCode, message);
        this.data = data;
    }
}
