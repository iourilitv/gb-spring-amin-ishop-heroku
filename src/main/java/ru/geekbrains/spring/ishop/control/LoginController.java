package ru.geekbrains.spring.ishop.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showMyLoginPage(HttpServletRequest request, HttpSession session) {
        //it is required in order to redirect on the previous page before the login form showing
        session.setAttribute("referer", request.getHeader("referer"));
        return "amin/login";
    }

}
