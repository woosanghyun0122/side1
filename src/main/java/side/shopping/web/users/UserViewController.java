package side.shopping.web.users;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import side.shopping.domain.users.Role;
import side.shopping.domain.users.Users;
import side.shopping.repository.users.UserRepository;
import side.shopping.repository.users.dto.users.FindUserDto;
import side.shopping.repository.users.dto.users.LoginDto;
import side.shopping.repository.users.dto.users.LoginResponseDto;
import side.shopping.web.users.service.UsersService;

import java.util.NoSuchElementException;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserViewController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private UserRepository repository;


    @GetMapping("/login")
    public String login(@RequestParam(value = "redirectURL", required = false) String redirectURL, Model model) {

        log.info("redirectURL={}", redirectURL);
        model.addAttribute("loginDto", new LoginDto());
        model.addAttribute("redirectURL", redirectURL);
        return "user/login";
    }

    @GetMapping("/myPage")
    public String myPage(HttpServletRequest request, Model model, HttpSession session) {

        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute("loginUser");

        log.info("session={}", loginUser.getUserId());
            Users userInfo = repository.findByUserid(loginUser.getUserId())
                    .orElseThrow(() -> new NoSuchElementException());
            model.addAttribute("loginInfo", userInfo);
            return "user/mypage";

    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    @GetMapping("/selectRole")
    public String selectRole() {
        return "user/selectRole";
    }

    @GetMapping("/signup")
    public String signup(@RequestParam("role") String role, Model model) {

        Role userRole = Role.valueOf(role);
        model.addAttribute("persistUserDto", Users.builder().role(userRole).build());
        return "user/signup";
    }

    @GetMapping("/find-id")
    public String findId(Model model) {
        model.addAttribute("findUserInfo", new FindUserDto());
        return "user/find_id";
    }
    @GetMapping("/find-pw")
    public String findPw(Model model) {
        model.addAttribute("findUserInfo", new FindUserDto());
        return "user/find_pw";
    }
}
