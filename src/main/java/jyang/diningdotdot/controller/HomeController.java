package jyang.diningdotdot.controller;

import jyang.diningdotdot.config.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final AuthenticationFacade authenticationFacade;

    @GetMapping("/")
    public String mainPage(Model model) {


        model.addAttribute("id", authenticationFacade.getCurrentUserId());
        model.addAttribute("name", authenticationFacade.getCurrentName());
        model.addAttribute("username", authenticationFacade.getCurrentUsername());


        return "main";
    }
}
