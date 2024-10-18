package side.shopping.web.users;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import side.shopping.domain.users.Users;
import side.shopping.repository.users.UserRepository;
import side.shopping.repository.users.dto.users.LoginDto;
import side.shopping.repository.users.dto.users.LoginResponseDto;
import side.shopping.web.users.service.UsersService;

import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserViewController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private UserRepository repository;


    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginDto", new LoginDto());
        return "user/login";
    }

    @GetMapping("/signUp")
    public String join() {
        return "user/signUp";
    }

    @GetMapping("/myPage")
    public String myPage(HttpServletRequest request, Model model,HttpSession session) {

        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute("loginUser");

        log.info("session={}", loginUser.getUserId());
        if(loginUser != null){
            Users userInfo = repository.findByUserid(loginUser.getUserId())
                    .orElseThrow(() -> new NoSuchElementException());
            model.addAttribute("loginInfo", userInfo);
            return "user/myPage";
        }
        else{
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
