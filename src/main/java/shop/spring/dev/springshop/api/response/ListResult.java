package shop.spring.dev.springshop.api.response;

import lombok.Getter;

import java.util.List;

@Getter
public class ListResult<T> extends CommonResult {

    private List<T> data;

    public ListResult(Boolean isSuccess, Integer statusCode, String message, List<T> data) {
        super(isSuccess, statusCode, message);
        this.data = data;
    }
}
