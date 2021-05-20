package project.watch_and_relax.web;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.watch_and_relax.model.binding.NewsAddBindingModel;
import project.watch_and_relax.model.service.ActorServiceModel;
import project.watch_and_relax.model.service.NewsServiceModel;
import project.watch_and_relax.model.service.PhotoServiceModel;
import project.watch_and_relax.model.view.NewsViewModel;
import project.watch_and_relax.service.NewService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/news")
public class NewController {
    private final NewService newService;
    private final ModelMapper modelMapper;

    public NewController(NewService newService, ModelMapper modelMapper) {
        this.newService = newService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public String addNewGetMethod(Model model, @AuthenticationPrincipal Principal principal, @RequestParam("id")String id){
        if(!model.containsAttribute("newsAddBindingModel")){
            model.addAttribute("newsAddBindingModel",new NewsAddBindingModel());
            model.addAttribute("username",principal.getName());
            model.addAttribute("id",id);
        }
        return "add-new";
    }
    @PostMapping("/add")
    public String addConfirm(@RequestParam("img") MultipartFile file,@RequestParam("id")String id, @Valid @ModelAttribute("newsAddBindingModel")
            NewsAddBindingModel newsAddBindingModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes, ModelAndView modelAndView){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("newsAddBindingModel",newsAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.newsAddBindingModel"
                    ,bindingResult);
            return "add-new";
        }
        PhotoServiceModel photoServiceModel=new PhotoServiceModel();
        photoServiceModel.setFile(file);
        this.newService.addNews(this.modelMapper.map(newsAddBindingModel,
                NewsServiceModel.class), photoServiceModel,id);
        return "redirect:/home";
    }
    @GetMapping("/new")
    public String showNews(@RequestParam("id")String id, Model model,@AuthenticationPrincipal Principal principal){
        NewsViewModel newById = this.newService.findNewById(id);
        model.addAttribute("username",principal.getName());
        model.addAttribute("currentnew",newById);
        return "news";
    }
    @GetMapping("/delete")
    public String deleteNews(@RequestParam("id")String id){
        this.newService.deleteNewById(id);
        return "redirect:/home";
    }
}
