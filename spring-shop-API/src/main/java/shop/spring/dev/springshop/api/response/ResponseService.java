package shop.spring.dev.springshop.api.response;

import org.springframework.stereotype.Service;
import shop.spring.dev.springshop.constant.BaseResponseStatus;

import java.util.List;

@Service
public class ResponseService {

    // 단일건 성공 결과를 처리하는 메소드
    public <T> SingleResult<T> getSingleResult(BaseResponseStatus status, T data) {
        return new SingleResult<>(true, status.getStatusCode(), status.getMessage(), data);
    }

    // 다중건 성공 결과를 처리하느 메소드
    public <T> ListResult<T> getListResult(BaseResponseStatus status, List<T> data) {
        return new ListResult<>(true, status.getStatusCode(), status.getMessage(), data);
    }

    // 성공 결과만 처리하는 메소드
    public CommonResult getSuccessResult(BaseResponseStatus status) {
        return new CommonResult(true, status.getStatusCode(), status.getMessage());
    }

    // 실패 결과만 처리하는 메소드
    public CommonResult getFailResult(BaseResponseStatus status) {
        return new CommonResult(false, status.getStatusCode(), status.getMessage());
    }
}
