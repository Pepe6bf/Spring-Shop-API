package shop.spring.dev.springshop.constant;

public enum BaseResponseStatus {

    // 로그인 성공/실패 메세지
    LOGIN_SUCCESS(200, "로그인 성공"),
    LOGIN_FAIL(400, "로그인 실패"),
    NOT_FOUND_USER(404, "회원을 찾을 수 없습니다."),

    // 회원 성공/실패 메세지
    CREATED_USER(201, "회원 가입 성공"),
    UPDATE_USER(200, "회원 정보 수정 성공"),
    DELETE_USER(200, "회원 탈퇴 성공"),

    // 상품 성공/실패 메세지
    READ_ITEM(200, "상품 데이터 조회 성공"),
    CREATED_ITEM(201, "상품 데이터 등록 성공"),
    UPDATED_ITEM(200, "상품 데이터 수정 성공"),

    // 5xx 에러 메세지
    INTERNAL_SERVER_ERROR(500, "서버 내부 에러"),
    DB_ERROR(500, "데이터베이스 에러");

    private final Integer statusCode;
    private final String message;

    BaseResponseStatus(Integer statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
