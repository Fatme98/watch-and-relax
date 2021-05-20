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
import project.watch_and_relax.model.binding.ActorAddBindingModel;
import project.watch_and_relax.model.binding.CommentAddBindingModel;
import project.watch_and_relax.model.entity.Photo;
import project.watch_and_relax.model.service.*;
import project.watch_and_relax.model.view.*;
import project.watch_and_relax.service.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/actor")
public class ActorController {
    private final ActorService actorService;
    private final ModelMapper modelMapper;
    private final FilmService filmService;
    private final CommentService commentService;
    private final NewService newService;
@Autowired
    public ActorController(ActorService actorService, ModelMapper modelMapper, FilmService filmService,
                           CommentService commentService, NewService newService) {
        this.actorService = actorService;
        this.modelMapper = modelMapper;
        this.filmService = filmService;
        this.commentService = commentService;

    this.newService = newService;
}
    @GetMapping("/add")
    public String add(@RequestParam("id")String id,Model model,@AuthenticationPrincipal Principal principal){
        if(!model.containsAttribute("actorAddBindingModel")){
            model.addAttribute("actorAddBindingModel",new ActorAddBindingModel());
            model.addAttribute("filmId",id);
            model.addAttribute("username",principal.getName());
        }
        return "add_actor";
    }
    @PostMapping("/add")
    public String addConfirm(@RequestParam("id")String id,@Valid @ModelAttribute("actorAddBindingModel")
                                     ActorAddBindingModel actorAddBindingModel,
                                   @RequestParam("img") MultipartFile file, BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes,ModelAndView modelAndView){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("actorAddBindingModel",actorAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.actorAddBindingModel"
                            ,bindingResult);
            return "add_actor";
        }
            PhotoServiceModel photoServiceModel=new PhotoServiceModel();
            photoServiceModel.setFile(file);
            FilmServiceModel filmServiceModel=this.filmService.findById(id);
            this.actorService.addActor(this.modelMapper.map(actorAddBindingModel,
                    ActorServiceModel.class),photoServiceModel,filmServiceModel);


        return "redirect:/home";
    }

    @PostMapping("/addComment")
    public ModelAndView comment(@RequestParam("id")String id, @Valid @ModelAttribute("commentAddBindingModel")
            CommentAddBindingModel commentAddBindingModel, ModelAndView modelAndView,@AuthenticationPrincipal Principal principal){
        CommentServiceModel commentServiceModel=this.modelMapper.map(commentAddBindingModel,CommentServiceModel.class);
        commentServiceModel.setActor_id(id);
        String filmId=this.actorService.findById(id).getFilmId();
        commentServiceModel.setUsername(principal.getName());
        this.commentService.addComment(commentServiceModel);
        modelAndView.setViewName("redirect:/home");
        return modelAndView;
    }
    @GetMapping("/seeComments")
    public ModelAndView listOfComments(@RequestParam("id")String id,ModelAndView modelAndView,
                                       @AuthenticationPrincipal Principal principal){
        modelAndView.setViewName("view-comments");
        modelAndView.addObject("username",principal.getName());
        ActorServiceModel actorServiceModel=this.actorService.findById(id);
        List<CommentViewModel> commentsForActor = this.actorService.getCommentsForActor(id);
        modelAndView.addObject("comments",commentsForActor);
        return modelAndView;
    }
    @GetMapping("/list")
    public ModelAndView listOfActors(@RequestParam("id")String id,ModelAndView modelAndView,
                                     @AuthenticationPrincipal Principal principal){
        List<ActorListViewModel> actors=this.filmService.getAllActorsFromThatFilm(id);
        modelAndView.addObject("username",principal);
        modelAndView.setViewName("actors");
        modelAndView.addObject("actors",actors);

       return modelAndView;
     }

    @GetMapping("/gallery")
    public ModelAndView gallery(@RequestParam("id")String id,ModelAndView modelAndView
    ,@AuthenticationPrincipal Principal principal){
        modelAndView.setViewName("gallery");
        List<PhotoGalleryViewModel> photosFromThatActor = this.actorService.getPhotosFromThatActor(id);
        ActorServiceModel actor = this.actorService.findById(id);
        if(actor.getUsername().equals(principal.getName())){
            modelAndView.addObject("isYour",true);
        }else{
            modelAndView.addObject("isYour",false);
        }
        modelAndView.addObject("photos",photosFromThatActor);
        modelAndView.addObject("username",principal.getName());

        return modelAndView;
    }
    @GetMapping("/actorInformation")
    public ModelAndView actorInformation(@RequestParam("id")String id, ModelAndView modelAndView,
                                         @AuthenticationPrincipal Principal principal){
        modelAndView.setViewName("actor_info");
        ActorServiceModel actorServiceModel=this.actorService.findById(id);
        ActorViewModel actorViewModel=this.modelMapper.map(actorServiceModel,ActorViewModel.class);
        actorViewModel.setPhotoUrl(actorServiceModel.getPhotoUrl());
        if(actorServiceModel.getUsername().equals(principal.getName())){
            modelAndView.addObject("isYourActor",true);
        }else{
            modelAndView.addObject("isYourActor",false);
        }
        modelAndView.addObject("actor",actorViewModel);
        modelAndView.addObject("username",principal.getName());
        modelAndView.addObject("commentAddBindingModel",new CommentAddBindingModel());
        return modelAndView;
    }
    @GetMapping("/videos")
    public ModelAndView videos(@RequestParam("id")String id,ModelAndView modelAndView,
                               @AuthenticationPrincipal Principal principal){
        modelAndView.setViewName("videos");
        List<VideoViewModel> videos=this.actorService.getAllVideosFromThatActor(id);
        ActorServiceModel actor = this.actorService.findById(id);
        if(actor.getUsername().equals(principal.getName())){
            modelAndView.addObject("isYour",true);
        }else{
            modelAndView.addObject("isYour",false);
        }
        modelAndView.addObject("videos",videos);
        modelAndView.addObject("username",principal.getName());
        return modelAndView;
    }
    @GetMapping("/youtube-videos")
    public ModelAndView youtubeVideos(@RequestParam("id")String id,ModelAndView modelAndView,
                                      @AuthenticationPrincipal Principal principal){
        modelAndView.setViewName("youtube-videos");
        List<YouTubeVideoViewModel>youTubeVideoViewModels=this.actorService.getAllYouTubeVideosFromThatActors(id);
        ActorServiceModel actor = this.actorService.findById(id);
        if(actor.getUsername().equals(principal.getName())){
            modelAndView.addObject("isYour",true);
        }else{
            modelAndView.addObject("isYour",false);
        }
        modelAndView.addObject("youtube",youTubeVideoViewModels);
        modelAndView.addObject("username",principal.getName());

        return modelAndView;
    }
    @GetMapping("/newsList")
    public String newsList(@RequestParam("id")String id,Model model,@AuthenticationPrincipal Principal principal){
        List<NewsListViewModel>news=this.actorService.getAllNewsTitles(id);
        ActorServiceModel actor = this.actorService.findById(id);
        if(actor.getUsername().equals(principal.getName())){
            model.addAttribute("isYour",true);
        }else{
            model.addAttribute("isYour",false);
        }
        model.addAttribute("username",principal.getName());
        model.addAttribute("news",news);
        return "news-list";
    }
    @GetMapping("/delete")
    public String deleteActor(@RequestParam("id")String id){
        this.actorService.deleteActorById(id);
        return "redirect:/home";
    }

}
