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
import project.watch_and_relax.model.binding.VideoAddBindingModel;
import project.watch_and_relax.model.entity.Video;
import project.watch_and_relax.model.service.VideoServiceModel;
import project.watch_and_relax.model.service.VideoSetServiceModel;
import project.watch_and_relax.service.ActorService;
import project.watch_and_relax.service.FilmService;
import project.watch_and_relax.service.PhotoService;
import project.watch_and_relax.service.VideoService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/video")
public class VideoController {
    private final VideoService videoService;
    private final ActorService actorService;
    private final FilmService filmService;
    private final ModelMapper modelMapper;
    @Autowired
    public VideoController(PhotoService photoService, VideoService videoService, ActorService actorService,
                                                                    FilmService filmService, ModelMapper modelMapper) {
        this.videoService = videoService;
        this.actorService = actorService;
        this.filmService = filmService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public String add(@RequestParam("id")String id,Model model,@AuthenticationPrincipal Principal principal){
        if(!model.containsAttribute("videoAddBindingModel")){
            model.addAttribute("videoAddBindingModel",new VideoAddBindingModel());
            model.addAttribute("id",id);
            model.addAttribute("username",principal.getName());
        }
        return "add_video";
    }
    @PostMapping("/add")
    public ModelAndView addConfirm(@RequestParam("id")String id,@RequestParam("video") MultipartFile file, @Valid
    @ModelAttribute("videoAddBindingModel")
            VideoAddBindingModel videoAddBindingModel,
                                   BindingResult bindingResult, RedirectAttributes redirectAttributes,
                                  ModelAndView modelAndView){
        if(bindingResult.hasErrors()){

            redirectAttributes.addFlashAttribute("videoAddBindingModel",videoAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.videoAddBindingModel",
                                                                                                         bindingResult);
            modelAndView.setViewName("add_video");
        }else{
            VideoServiceModel videoServiceModel =this.modelMapper.map(videoAddBindingModel, VideoServiceModel.class);
            videoServiceModel.setMultipartFile(file);
            if(videoAddBindingModel.getFilmName().equals("")&&!videoAddBindingModel.getActorName().equals("")){
                videoServiceModel.setActorId(id);
            }
            if(videoAddBindingModel.getActorName().equals("")&&!videoAddBindingModel.getFilmName().equals("")){
                videoServiceModel.setFilmId(id);
            }
            VideoSetServiceModel videoSetServiceModel = this.videoService.addVideo(videoServiceModel,id);
            Video video=this.modelMapper.map(videoSetServiceModel,Video.class);

            modelAndView.setViewName("redirect:/home");
        }
        return modelAndView;
    }
    @GetMapping("/delete")
    public String deleteVideo(@RequestParam("id")String id){
        this.videoService.deleteById(id);
        return "redirect:/home";
    }

}
