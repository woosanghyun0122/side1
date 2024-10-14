package side.shopping.web.users;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import side.shopping.repository.users.dto.users.LoginDto;
import side.shopping.web.users.service.UsersService;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserViewController {

    @Autowired
    private UsersService usersService;

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
    public String myPage(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();

        if(session != null){
            model.addAttribute("loginInfo", session.getAttribute("loginInfo"));
            return "user/myPage";
        }
        else{
            return "redirect:/login";
        }
    }
}
