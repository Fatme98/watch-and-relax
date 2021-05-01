package project.watch_and_relax.web;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.watch_and_relax.model.binding.SearchAddBindingModel;
import project.watch_and_relax.model.binding.UserLoginBindingModel;
import project.watch_and_relax.model.binding.UserRegisterBindingModel;
import project.watch_and_relax.model.service.*;
import project.watch_and_relax.model.view.UserViewModel;
import project.watch_and_relax.service.CommentService;
import project.watch_and_relax.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final CommentService commentService;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper, CommentService commentService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.commentService = commentService;
    }


    @GetMapping("/register")
    public String register(Model model) {
        if (!model.containsAttribute("userRegisterBindingModel")) {
            model.addAttribute("userRegisterBindingModel", new UserRegisterBindingModel());
        }
        return "register";
    }

    @PostMapping("/register")
    public String registerConfirm(@RequestParam("img") MultipartFile file,
                                                                      @Valid @ModelAttribute("userRegisterBindingModel")
                                          UserRegisterBindingModel userRegisterBindingModel,
                                  BindingResult bindingResult,HttpSession httpSession, RedirectAttributes redirectAttributes,Model model) {
        if (bindingResult.hasErrors() || !
                userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.
                    addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel",
                                                                                                         bindingResult);
            return "register";
        }
        if(userService.existsUser(userRegisterBindingModel.getUsername())){
            bindingResult.rejectValue
                    ("username","error.username","An account with this username already exists.");
            return "register";
        }
            PhotoServiceModel photoServiceModel=new PhotoServiceModel();
            photoServiceModel.setFile(file);
            UserServiceModel userServiceModel=this.modelMapper.map(userRegisterBindingModel, UserServiceModel.class);
            userServiceModel.setPhotoServiceModel(photoServiceModel);
            this.userService.createAndLoginUser(userServiceModel);

            model.addAttribute("name",httpSession.getAttribute("application-name"));

        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        if (!model.containsAttribute("userLoginBindingModel")) {
            model.addAttribute("userLoginBindingModel", new UserLoginBindingModel());
        }

        return "login";
    }

    @PostMapping("/login")
    public String loginConfirm(@Valid @ModelAttribute("userLoginBindingModel")
                                       UserLoginBindingModel userLoginBindingModel,
                                     BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("userLoginBindingModel", userLoginBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLoginBindingModel",
                    bindingResult);
           return "login";
        }
        UserViewModel user=this.userService.findUserByUsername(userLoginBindingModel.getUsername());
        if (user == null) {
            redirectAttributes.addFlashAttribute("userLoginBindingModel", userLoginBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLoginBindingModel",
                    bindingResult);
            return "login";
        }

        this.userService.loginUser(user.getUsername(),userLoginBindingModel.getPassword());
        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/";
    }
    @GetMapping("/delete")
    public String deleteYourProfile(@RequestParam("username")String username){
        return "redirect:/home";
    }

    @GetMapping("/profile")
    public ModelAndView profile(@RequestParam("username")String username, ModelAndView modelAndView,
                                @AuthenticationPrincipal Principal principal ) {
        modelAndView.setViewName("profile");
        UserViewModel userViewModel=this.userService.findUser(username);
        if(username.equals(principal.getName())){
            modelAndView.addObject("isYourProfile",true);
        }else{
            modelAndView.addObject("isYourProfile",false);
        }
        modelAndView.addObject("user",userViewModel);
        modelAndView.addObject("username",principal.getName());
        modelAndView.addObject("searchForUserBindingModel",new SearchAddBindingModel());
        modelAndView.addObject("searchForActorBindingModel",new SearchAddBindingModel());
        modelAndView.addObject("searchForFilmBindingModel",new SearchAddBindingModel());
        return modelAndView;
    }

    @GetMapping("/info")
    public ModelAndView info(@RequestParam("username")String username,ModelAndView modelAndView,
                             @AuthenticationPrincipal Principal principal ) {
        modelAndView.setViewName("user_info");
        UserViewModel userViewModel=this.userService.findUser(username);
        modelAndView.addObject("user",userViewModel);
        modelAndView.addObject("username",principal.getName());

        return modelAndView;
    }
    @GetMapping("/all-publications")
    public ModelAndView publications(@RequestParam("username")String username,ModelAndView modelAndView,
                                     @AuthenticationPrincipal Principal principal){
        modelAndView.setViewName("users-publications");
        modelAndView.addObject("users",this.userService.
                getAllUsersPublicationsExceptTheUserThatIsLoggedIn(username));
        modelAndView.addObject("username",principal.getName());
        return modelAndView;
    }
    @GetMapping("/favourite")
    public ModelAndView favourites(@RequestParam("username")String username,ModelAndView modelAndView,
                                     @AuthenticationPrincipal Principal principal){
        modelAndView.setViewName("favourites");
        modelAndView.addObject("favourites",this.userService.getAllFavourites(username));
        modelAndView.addObject("username",principal.getName());
        return modelAndView;
    }
    @GetMapping("deleteComments")
    public String deleteComments(@RequestParam("id")String id){
        this.commentService.deleteById(id);
        return "redirect:/home";
    }

}