package project.watch_and_relax.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.watch_and_relax.model.view.RoleViewModel;
import project.watch_and_relax.service.StatsService;
import project.watch_and_relax.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class StatsController {
    private final StatsService statsService;
    private final UserService userService;

    public StatsController(StatsService statsService, UserService userService){
        this.statsService=statsService;
        this.userService = userService;
    }
    @GetMapping("/stats")
    public String stats(Model model, @RequestParam("username")String username){

        if(this.userService.isAdmin(username)){
            model.addAttribute("requestCount",statsService.getRequestCount());
            model.addAttribute("startedOn",statsService.getStartedOn());
            model.addAttribute("username",username);
            return "stats";
        }else{
            return null;
        }

    }

}
