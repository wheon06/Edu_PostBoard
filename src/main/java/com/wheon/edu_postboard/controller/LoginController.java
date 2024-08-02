package com.wheon.edu_postboard.controller;

import com.wheon.edu_postboard.dto.LoginRequestDto;
import com.wheon.edu_postboard.dto.LoginResponseDto;
import com.wheon.edu_postboard.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @GetMapping("/")
    public String loginP(Model model, LoginRequestDto requestDto) {
        model.addAttribute("requestDto", requestDto);
        return "login";
    }

    @PostMapping("/loginProc")
    public String loginProc(@ModelAttribute("requestDto") LoginRequestDto requestDto, HttpServletRequest request) {
        LoginResponseDto responseDto = memberService.login(requestDto);

        if (responseDto == null) {
            return "redirect:/";
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("userInfo", responseDto);
            return "redirect:/main";
        }

    }

}
