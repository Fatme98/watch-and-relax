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
import project.watch_and_relax.model.binding.PhotoAddBindingModel;
import project.watch_and_relax.model.entity.Actor;
import project.watch_and_relax.model.entity.Film;
import project.watch_and_relax.model.service.*;
import project.watch_and_relax.model.view.ActorViewModel;
import project.watch_and_relax.service.ActorService;
import project.watch_and_relax.service.FilmService;
import project.watch_and_relax.service.PhotoService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/photo")
public class PhotoController {
    private final PhotoService photoService;
    private final ActorService actorService;
    private final FilmService filmService;
    private final ModelMapper modelMapper;
@Autowired
    public PhotoController(PhotoService photoService, ActorService actorService, FilmService filmService,
                                                                                             ModelMapper modelMapper) {
        this.photoService = photoService;
    this.actorService = actorService;
    this.filmService = filmService;
    this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public String add(@RequestParam("id")String id,Model model,@AuthenticationPrincipal Principal principal ){
        if(!model.containsAttribute("photoAddBindingModel")){
            model.addAttribute("photoAddBindingModel",new PhotoAddBindingModel());
            model.addAttribute("id",id);
            model.addAttribute("username",principal.getName());
        }
        return "add_photo";
    }
    @PostMapping("/add")
    public ModelAndView addConfirm(@RequestParam("id")String id,@RequestParam("img") MultipartFile file, @Valid
                             @ModelAttribute("photoAddBindingModel")
                                     PhotoAddBindingModel photoAddBindingModel,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes,
                                   ModelAndView modelAndView){
        if(bindingResult.hasErrors()){

            redirectAttributes.addFlashAttribute("photoAddBindingModel",photoAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.photoAddBindingModel",
                                                                                                         bindingResult);
           modelAndView.setViewName("add_photo");
        }else{
            PhotoServiceModel photoServiceModel=this.modelMapper.map(photoAddBindingModel, PhotoServiceModel.class);
            photoServiceModel.setFile(file);

            if(!photoAddBindingModel.getActorName().equals("")&&photoAddBindingModel.getFilmName().equals("")){
                photoServiceModel.setActorName(photoAddBindingModel.getActorName());
                photoServiceModel.setActorId(id);
                PhotoProfileServiceModel photoProfileServiceModel=this.photoService.addPhoto(photoServiceModel);
            }
            if(!photoAddBindingModel.getFilmName().equals("")&&photoAddBindingModel.getActorName().equals("")){
                photoServiceModel.setFilmName(photoAddBindingModel.getFilmName());
                photoServiceModel.setFilmId(id);
                PhotoProfileServiceModel photoProfileServiceModel=this.photoService
                        .addPhoto(photoServiceModel);
            }
            modelAndView.setViewName("redirect:/home");

        }
        return modelAndView;
    }
    @GetMapping("/delete")
    String deletePhoto(@RequestParam("id")String id){
       this.photoService.deleteById(id);
       return "redirect:/home";
    }

}
