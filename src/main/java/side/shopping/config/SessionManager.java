package side.shopping.config;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import side.shopping.exception.CustomException;
import side.shopping.exception.ErrorCode;
import side.shopping.repository.users.dto.users.LoginResponseDto;

import java.util.Optional;

@Component
public class SessionManager {

    @Autowired
    private HttpSession session;

    public LoginResponseDto getLoginUser() {

        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute("loginUser");
        if (loginUser == null) {
            throw new CustomException(ErrorCode.LOGIN_ERROR.getCode(), ErrorCode.LOGIN_ERROR.getMessage());
        }

        return loginUser;
    }

    public void clear() {
        session.removeAttribute("loginUser");
    }
}
