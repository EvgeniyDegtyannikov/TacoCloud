package tacos.web.controllers;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String login() {
        return "login";
    }

    @GetMapping("/error")
    public String loginOnFailure(Model model, HttpServletRequest httpServletRequest) {
        String errorMessage = null;
        HttpSession session = httpServletRequest.getSession();
        if (session != null) {
            AuthenticationException exception = (AuthenticationException) session
                    .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            if (exception != null)
                errorMessage = exception.getMessage();
        }
        model.addAttribute("errorMessage", errorMessage);
        return "login";
    }
}
