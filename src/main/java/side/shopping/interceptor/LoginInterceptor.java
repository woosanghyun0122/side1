package side.shopping.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        String queryParameter = request.getQueryString();

        if (StringUtils.hasText(queryParameter)) {
            requestURI = requestURI + "?" + queryParameter;
        }

        log.info("인증 체크 인터셉터 실행 {}", requestURI);
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("loginUser") == null) {
            log.info("미인증 사용자 요청");
            response.sendRedirect("/user/login?redirectURL="+requestURI);
            return false;
        }
        return true;
    }
}
