package jyang.diningdotdot.controller;

import jakarta.validation.Valid;
import jyang.diningdotdot.dto.common.JoinForm;
import jyang.diningdotdot.service.PartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/partners")
@RequiredArgsConstructor
public class PartnerController {

    private final PartnerService partnerService;

    @GetMapping("/join")
    public String joinPage(Model model) {
        model.addAttribute("joinForm", new JoinForm());
        return "partners/join";
    }

    @PostMapping("/joinProc")
    public String joinProcess(
            @ModelAttribute @Valid JoinForm joinForm,
            BindingResult result) {
        if (result.hasErrors()) {
            return "partners/join";
        }
        partnerService.joinProcess(joinForm);
        return "redirect:/login";
    }

}
