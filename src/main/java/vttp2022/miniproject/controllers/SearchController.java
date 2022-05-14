package vttp2022.miniproject.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vttp2022.miniproject.models.Show;
import vttp2022.miniproject.services.SearchService;
import vttp2022.miniproject.services.ShowService;

@Controller
@RequestMapping
public class SearchController {

    @Autowired
    private SearchService searchSvc;

    @Autowired
    private ShowService showSvc;

    @GetMapping(path="/searchpage")
    public ModelAndView getSearchPage() {

        ModelAndView mvc = new ModelAndView();

        mvc.setViewName("search");

        return mvc;
    }

    @GetMapping(path="/search")
    public ModelAndView getSearch(@RequestParam(name="search_name") String search, HttpSession session) {

        System.out.printf(">>>>> %s\n", search);

        List<Show> shows = searchSvc.getTitlesByNameFromDb(search);
        String username = session.getAttribute("username").toString();

        ModelAndView mvc = new ModelAndView();

        mvc.setViewName("result");
        mvc.setStatus(HttpStatus.OK);
        mvc.addObject("username", username.substring(0, 1).toUpperCase() + username.substring(1));
        mvc.addObject("search", search);
        mvc.addObject("shows", shows);

        return mvc;
    }

    // @PostMapping(path="/saved")
    // public ModelAndView postFavourites(HttpServletRequest request, HttpSession session) {

    //     String username = session.getAttribute("username").toString();
    //     String password = session.getAttribute("password").toString();

    //     String[] flags = request.getParameterValues("flag");

    //     System.out.println(">>>>>>Flags: " + flags);

    //     // try {
    //     //     flag = showSvc.saveShow(username, password, show)
    //     // } catch(Exception e) {
    //     //     e.printStackTrace();
    //     // }

    //     ModelAndView mvc = new ModelAndView();

    //     return mvc;
        
    }

    @GetMapping(path="/favourites")
    public ModelAndView getFavourites(HttpSession session) {

        String username = session.getAttribute("username").toString();

        List<Show> savedShows = showSvc.getShowsByUsername(username);

        ModelAndView mvc = new ModelAndView();

        mvc.setViewName("favourites");
        mvc.addObject("username", username.substring(0, 1).toUpperCase() + username.substring(1));
        mvc.addObject("savedShows", savedShows);

        return mvc;

    }

}
