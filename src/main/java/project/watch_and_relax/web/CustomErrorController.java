package project.watch_and_relax.web;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Constraint;
import java.security.Principal;

public abstract class CustomErrorController implements ErrorController {
    public String getErrorPath(Model model, @AuthenticationPrincipal Principal principal) {
        model.addAttribute("username",principal.getName());
        return "/error";
    }
}
