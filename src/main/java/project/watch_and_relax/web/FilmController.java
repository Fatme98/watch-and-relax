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
import project.watch_and_relax.model.binding.CommentAddBindingModel;
import project.watch_and_relax.model.binding.FilmAddBindingModel;
import project.watch_and_relax.model.entity.Photo;
import project.watch_and_relax.model.service.*;
import project.watch_and_relax.model.view.*;
import project.watch_and_relax.service.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/film")
public class FilmController {
    private final FilmService filmService;
    private final ModelMapper modelMapper;
    private final CommentService commentService;
    private final UserService userService;
    private final NewService newService;
    private final ActorService actorService;
@Autowired
    public FilmController(FilmService filmService, ModelMapper modelMapper, CommentService commentService,
                          UserService userService, NewService newService, ActorService actorService) {
         this.filmService = filmService;
         this.modelMapper = modelMapper;
         this.commentService = commentService;
         this.userService = userService;
    this.newService = newService;
    this.actorService = actorService;
}


    @GetMapping("/add")
    public String add(@RequestParam("username")String username,Model model){
        if(!model.containsAttribute("filmAddBindingModel")){
            model.addAttribute("filmAddBindingModel",new FilmAddBindingModel());
            model.addAttribute("username",username);
            model.addAttribute("commentAddBindingModel",new CommentAddBindingModel());
        }
        return "add_film";
    }
    @PostMapping("/add")
    public String addConfirm(@RequestParam("username")String username,@RequestParam("img") MultipartFile file,
                                                                         @Valid @ModelAttribute("filmAddBindingModel")
                                     FilmAddBindingModel filmAddBindingModel,
                             BindingResult bindingResult,RedirectAttributes redirectAttributes,Model model){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("filmAddBindingModel",filmAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.filmAddBindingModel",
                                                                                                        bindingResult);
            return "add_film";
        }
        PhotoServiceModel photoServiceModel=new PhotoServiceModel();
        photoServiceModel.setFile(file);
        this.filmService.addFilmToUser(this.modelMapper.map(filmAddBindingModel,
                FilmServiceModel.class),photoServiceModel,username);
        return "redirect:/home";
    }
    @GetMapping("/info")
    public ModelAndView filmInfo(@RequestParam("id")String id, ModelAndView modelAndView,
                                 @AuthenticationPrincipal Principal principal){
        FilmServiceModel filmServiceModel=this.filmService.findById(id);
        FilmViewModel filmViewModel=this.modelMapper.map(filmServiceModel,FilmViewModel.class);
        filmViewModel.setPhotoUrl(filmServiceModel.getPhotoUrl());
        modelAndView.addObject("film",filmViewModel);
        if(filmServiceModel.getUsername().equals(principal.getName())){
            modelAndView.addObject("isYour",true);
        }else{
            modelAndView.addObject("isYour",false);
        }
        modelAndView.setViewName("film");
        modelAndView.addObject("commentAddBindingModel",new CommentAddBindingModel());
        modelAndView.addObject("username",principal.getName());
        return modelAndView;
    }

    @PostMapping("/addComment")
    public ModelAndView comment(@RequestParam("id")String id, @Valid @ModelAttribute("commentAddBindingModel")
            CommentAddBindingModel commentAddBindingModel,ModelAndView modelAndView,@AuthenticationPrincipal Principal principal){
        CommentServiceModel commentServiceModel=this.modelMapper.map(commentAddBindingModel,
                CommentServiceModel.class);
        commentServiceModel.setFilm_id(id);
        commentServiceModel.setUsername(principal.getName());
        this.commentService.addComment(commentServiceModel);
        modelAndView.setViewName("redirect:/home");

        return modelAndView;
    }
    @GetMapping("/seeComments")
    public ModelAndView listOfComments(@RequestParam("id")String id,ModelAndView modelAndView,
                                       @AuthenticationPrincipal Principal principal){
        modelAndView.setViewName("view-comments");
        List<CommentViewModel> commentsForFilm = this.filmService.getCommentsForFilm(id);
        modelAndView.addObject("filmId",id);
        modelAndView.addObject("username",principal.getName());
        modelAndView.addObject("commentAddBindingModel",new CommentAddBindingModel());
        modelAndView.addObject("comments",commentsForFilm);
        return modelAndView;
    }

    @GetMapping("/gallery")
    public ModelAndView gallery(@RequestParam("id")String id,ModelAndView modelAndView
    ,@AuthenticationPrincipal Principal principal){
        List<PhotoGalleryViewModel> allPicturesFromThatFilm = this.filmService.getAllPicturesFromThatFilm(id);
        FilmServiceModel film = this.filmService.findById(id);
        if(film.getUsername().equals(principal.getName())){
            modelAndView.addObject("isYour",true);
        }else{
            modelAndView.addObject("isYour",false);
        }
        modelAndView.setViewName("gallery");
        modelAndView.addObject("photos",allPicturesFromThatFilm);
        modelAndView.addObject("username",principal.getName());
        return modelAndView;
    }
    @GetMapping("/videos")
    public ModelAndView videos(@RequestParam("id")String id,ModelAndView modelAndView,
                               @AuthenticationPrincipal Principal principal){
        modelAndView.setViewName("videos");
        List<VideoViewModel>videoViewModels=this.filmService.getAllVideosFromThatFilm(id);
        FilmServiceModel film = this.filmService.findById(id);
        if(film.getUsername().equals(principal.getName())){
            modelAndView.addObject("isYour",true);
        }else{
            modelAndView.addObject("isYour",false);
        }
        modelAndView.addObject("videos",videoViewModels);
        modelAndView.addObject("username",principal.getName());
        return modelAndView;
    }
    @GetMapping("/youtube-videos")
    public ModelAndView youtubeVideos(@RequestParam("id")String id,ModelAndView modelAndView
    ,@AuthenticationPrincipal Principal principal){
        modelAndView.setViewName("youtube-videos");
        List<YouTubeVideoViewModel>youTubeVideoViewModels=this.filmService.getAllYouTubeVideosFromThatFilm(id);
        FilmServiceModel film = this.filmService.findById(id);
        if(film.getUsername().equals(principal.getName())){
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
        List<NewsListViewModel>news=this.filmService.getAllNewsTitles(id);
        FilmServiceModel film = this.filmService.findById(id);
        if(film.getUsername().equals(principal.getName())){
            model.addAttribute("isYour",true);
        }else{
            model.addAttribute("isYour",false);
        }
        model.addAttribute("username",principal.getName());
        model.addAttribute("news",news);
        return "news-list";
    }
    @GetMapping("/delete")
    public String deleteFilm(@RequestParam("id")String id){
        List<ActorListViewModel> allActorsFromThatFilm = this.filmService.getAllActorsFromThatFilm(id);
        for (ActorListViewModel actorListViewModel : allActorsFromThatFilm) {
            List<NewsListViewModel> allNewsTitles = this.actorService.getAllNewsTitles(actorListViewModel.getId());
            for (NewsListViewModel allNewsTitle : allNewsTitles) {
                this.newService.deleteNewById(allNewsTitle.getId());
            }
        }
        List<NewsListViewModel> allNewsTitlesForFilm = this.filmService.getAllNewsTitles(id);
        for (NewsListViewModel allNewsTitle : allNewsTitlesForFilm) {
            this.newService.deleteNewById(allNewsTitle.getId());
        }
        this.filmService.deleteFilmById(id);
        return "redirect:/home";
    }

}
