package side.shopping.exception;

public enum ErrorCode {

    NO_ARGUMENT_USERID(1001,"아이디를 입력해주세요"),
    NOT_MATCHED_USERID(1002, "아이디는 4자 이상 10자 미만으로 입력해주세요"),
    NOT_MATCHED_NICKNAME(1003,"닉네임은 2자 이상 10자 이하로 입력해주세요"),
    NO_ARGUMENT_NICKNAME(1004,"닉네임을 입력해주세요"),
    INVALID_USERID(1005,"아이디가 존재하지 않습니다."),
    INVALID_PASSWORD(1006,"비밀번호가 잘못되었습니다."),
    VARIABLE_ERROR(1007,"오류가 발생하였습니다."),
    SERVER_ERROR(9000,"알 수 없는 오류가 발생하였습니다."),
    SAVE_ERROR(9001,"저장에 실패하였습니다."),
    UPDATE_ERROR(9002,"수정에 실패하였습니다."),
    DELETE_ERROR(9003, "저장에 실패하였습니다."),
    SELECT_ERROR(9004, "조회에 실패하였습니다."),
    LOGIN_ERROR(9005, "로그인 상태가 아닙니다"),
    PAYMENT_ERROR(9006, "결제 중 오류가 발생하였습니다.")
    ;

    private final int code;
    private final String message;


    ErrorCode(int code,String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
