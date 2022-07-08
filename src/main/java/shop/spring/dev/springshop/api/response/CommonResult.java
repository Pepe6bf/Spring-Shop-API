package shop.spring.dev.springshop.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommonResult {

    private Boolean isSuccess;

    private Integer statusCode;

    private String message;
}
