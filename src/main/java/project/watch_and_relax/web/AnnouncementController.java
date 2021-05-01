package project.watch_and_relax.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.watch_and_relax.model.binding.AnnouncementAddBindingModel;
import project.watch_and_relax.service.AnnouncementService;
import project.watch_and_relax.service.UserService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/announcements")
public class AnnouncementController {
    private final AnnouncementService announcementService;
    private final UserService userService;
@Autowired
    public AnnouncementController(AnnouncementService announcementService, UserService userService) {
        this.announcementService = announcementService;
        this.userService = userService;
}
    @GetMapping
    public String announcement(Model model, @RequestParam("username")String username){
       model.addAttribute("username",username);
       model.addAttribute("announcements",announcementService.findAll());
       model.addAttribute("isAdmin",this.userService.isAdmin(username));
       return "announcements";
    }

    @GetMapping("/new")
    public String newAnnouncement(Model model,@RequestParam("username")String username){
        if(this.userService.isAdmin(username)){
            AnnouncementAddBindingModel announcementAddBindingModel;
            if(model.containsAttribute("announcementAddBindingModel")){
                announcementAddBindingModel=(AnnouncementAddBindingModel)model.
                        getAttribute("announcementAddBindingModel");
            }else{
                announcementAddBindingModel=new AnnouncementAddBindingModel();
            }
            model.addAttribute("announcementAddBindingModel",announcementAddBindingModel);
            model.addAttribute("username",username);
            return "new";
        }
        return "redirect:/home";
    }
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("announcementAddBindingModel")
                                   AnnouncementAddBindingModel announcementAddBindingModel,
                       BindingResult bindingResult, RedirectAttributes redirectAttributes,
                                                   @RequestParam("username")String username){
     if(this.userService.isAdmin(username)){
         if(bindingResult.hasErrors()){
             redirectAttributes.addFlashAttribute("announcementAddBindingModel",announcementAddBindingModel);
             redirectAttributes.
                     addFlashAttribute("org.springframework.validation.BindingResult.announcementAddBindingModel"
                             ,bindingResult);
             return "redirect:/announcements/new";
         }
         announcementService.createOrUpdateAnnouncement(announcementAddBindingModel);
     }
      return "redirect:/home";
    }
    @GetMapping("/delete")
    public String delete(@RequestParam("username")String username){
    if(this.userService.isAdmin(username)){
        announcementService.deleteAll();
    }
    return "redirect:/home";
  }

}
