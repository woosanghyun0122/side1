package side.shopping.web.zzim;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import side.shopping.config.SessionManager;
import side.shopping.domain.zzim.Zzim;
import side.shopping.repository.users.dto.users.LoginResponseDto;
import side.shopping.web.zzim.service.ZzimService;

@Slf4j
@Controller
public class ZzimController {

    @Autowired
    private ZzimService service;

    @Autowired
    private SessionManager sessionManager;



    /**
     * 찜 리스트 화면 조회
     */
    @GetMapping("/zzim")
    public String zzimList(Model model) {

        String userid = sessionManager.getLoginUser().getUserId();
        model.addAttribute("list", service.findZzimList(userid));
        return "/zzim/list";
    }

    /**
     * 찜 저장
     */
    @ResponseBody
    @PostMapping("/zzim/{productId}")
    public ResponseEntity saveZzim(@PathVariable Long productId) {

        String userid = sessionManager.getLoginUser().getUserId();
        Zzim zzim = service.saveZzim(productId,userid);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 찜 삭제
     */
    @ResponseBody
    @DeleteMapping("/zzim/{productId}")
    public ResponseEntity deleteZzim(@PathVariable Long productId) {

        String userid = sessionManager.getLoginUser().getUserId();
        service.delete(productId, userid);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
