package side.shopping.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {

    private int statusCode;
    private String message;
    private int code;

    public ErrorResponse(int statusCode, String message, int code) {
        this.statusCode = statusCode;
        this.message = message;
        this.code = code;
    }
}
