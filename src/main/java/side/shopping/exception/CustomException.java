package side.shopping.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class CustomException extends RuntimeException {

    private int code;
    private String message;

    public CustomException(int code, String message) {
        this.message = message;
        this.code = code;
    }
}
