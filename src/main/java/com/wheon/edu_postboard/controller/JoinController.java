package com.wheon.edu_postboard.controller;

import com.wheon.edu_postboard.dto.JoinRequestDto;
import com.wheon.edu_postboard.dto.LoginRequestDto;
import com.wheon.edu_postboard.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class JoinController {

    private final MemberService memberService;

    @GetMapping("/join")
    public String joinP(Model model, JoinRequestDto requestDto) {
        model.addAttribute("requestDto", requestDto);
        return "join";
    }

    @PostMapping("/joinProc")
    public String joinProc(@ModelAttribute("requestDto") JoinRequestDto requestDto) {

        if (!memberService.join(requestDto)) {
            return "redirect:/join";
        }

        return "redirect:/";
    }

    @RequestMapping("/logout")
    public String logoutProc(HttpSession session) throws Exception {
        session.invalidate();
        return "redirect:/";
    }

}
