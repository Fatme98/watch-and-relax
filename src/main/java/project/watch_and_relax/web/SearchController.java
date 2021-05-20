package project.watch_and_relax.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.watch_and_relax.model.binding.SearchAddBindingModel;
import project.watch_and_relax.model.view.ActorViewModel;
import project.watch_and_relax.model.view.FilmViewModel;
import project.watch_and_relax.model.view.UserViewModel;
import project.watch_and_relax.service.ActorService;
import project.watch_and_relax.service.FilmService;
import project.watch_and_relax.service.UserService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/search")
public class SearchController {
    private final UserService userService;
    private final FilmService filmService;
    private final ActorService actorService;
    public SearchController(UserService userService, FilmService filmService, ActorService actorService) {
        this.userService = userService;
        this.filmService = filmService;
        this.actorService = actorService;
    }

    @PostMapping("/user")
    public ModelAndView searchUser(@Valid @ModelAttribute("searchAddBindingModel")
                             SearchAddBindingModel searchAddBindingModel, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes, ModelAndView modelAndView,
                             @AuthenticationPrincipal Principal principal ){
        UserViewModel userViewModel=this.userService.findUser(searchAddBindingModel.getName());
        modelAndView.addObject("user",userViewModel);
        modelAndView.addObject("username",principal.getName());
        modelAndView.addObject("isUserSearched",true);
        modelAndView.addObject("isFilmSearched",false);
        modelAndView.addObject("isActorSearched",false);
        modelAndView.setViewName("search-result");
        return modelAndView;
    }
    @PostMapping("/film")
    public ModelAndView searchFilm(@Valid @ModelAttribute("searchAddBindingModel")
                                     SearchAddBindingModel searchAddBindingModel, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes, ModelAndView modelAndView,
                            @AuthenticationPrincipal Principal principal ){
        FilmViewModel filmViewModel=this.filmService.findByName(searchAddBindingModel.getName());
        modelAndView.addObject("film",filmViewModel);
        modelAndView.addObject("username",principal.getName());
        modelAndView.addObject("isUserSearched",false);
        modelAndView.addObject("isFilmSearched",true);
       modelAndView.addObject("isActorSearched",false);
        modelAndView.setViewName("search-result");
        return modelAndView;
    }
    @PostMapping("/actor")
    public ModelAndView searchActor(@Valid @ModelAttribute("searchAddBindingModel")
                                     SearchAddBindingModel searchAddBindingModel, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes, ModelAndView modelAndView,
                             @AuthenticationPrincipal Principal principal ){
        ActorViewModel actorViewModel=this.actorService.findActorByName(searchAddBindingModel.getName());
        modelAndView.addObject("actor",actorViewModel);
        modelAndView.addObject("username",principal.getName());
        modelAndView.addObject("isUserSearched",false);
        modelAndView.addObject("isFilmSearched",false);
        modelAndView.addObject("isActorSearched",true);
        modelAndView.setViewName("search-result");
        return modelAndView;
    }

}
