package jyang.diningdotdot.controller;

import jakarta.validation.Valid;
import jyang.diningdotdot.dto.common.JoinForm;
import jyang.diningdotdot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/join")
    public String joinPage(Model model) {
        model.addAttribute("joinForm", new JoinForm());
        return "user/join";
    }

    @PostMapping("/joinProc")
    public String joinProcess(
            @ModelAttribute @Valid JoinForm joinForm,
            BindingResult result) {
        if (result.hasErrors()) {
            return "user/join";
        }
        userService.joinProcess(joinForm);
        return "redirect:/login";
    }


}
