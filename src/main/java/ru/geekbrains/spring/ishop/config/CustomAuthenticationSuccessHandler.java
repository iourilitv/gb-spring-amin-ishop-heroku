package ru.geekbrains.spring.ishop.config;

import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.spring.ishop.entity.User;
import ru.geekbrains.spring.ishop.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private IUserService userService;

    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        String userName = authentication.getName();
        User theUser = userService.findByUserName(userName);
        HttpSession session = request.getSession();
        session.setAttribute("user", theUser);
        String referer = String.valueOf(request.getSession().getAttribute("referer"));
        if(referer.contains("register")) {
            response.sendRedirect(request.getContextPath());
        } else {
            response.sendRedirect(referer);
        }
        session.removeAttribute("referer");
    }

}

