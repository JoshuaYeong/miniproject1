package vttp2022.miniproject.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

        // System.out.printf(">>>>> %s\n", search);

        System.out.println(">>>>> Search Term: " + search);

        List<Show> shows = searchSvc.getTitlesByNameFromDb(search);
        String username = (String) session.getAttribute("username");

        ModelAndView mvc = new ModelAndView();

        mvc.setViewName("result");
        mvc.setStatus(HttpStatus.OK);
        mvc.addObject("username", username.substring(0, 1).toUpperCase() + username.substring(1));
        mvc.addObject("search_name", search);
        mvc.addObject("shows", shows);

        return mvc;
    }

    @PostMapping(path="/saved", consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView postFavourites(@RequestBody MultiValueMap<String, String> form, HttpSession session) {

        ModelAndView mvc = new ModelAndView();

        String username = (String) session.getAttribute("username");
        String password = (String) session.getAttribute("password");

        List<String> list = form.get("selected");

        System.out.println(">>>>>> list: " + list);

        if (!list.isEmpty()) {
                Show show = new Show();
                show.setShowName(form.getFirst("${show.showName}"));
                show.setType(form.getFirst("${show.type}"));
                show.setId(Integer.valueOf(form.getFirst("${show.id}")));
                show.setYear(Integer.valueOf(form.getFirst("${show.year}")));
                show.setImageUrl(form.getFirst("${show.imageUrl}"));
                
                try {
                    boolean success = showSvc.saveShow(username, password, show);
                    System.out.println(">>>>>> success: " +success);
                } catch (Exception e) {
                    e.getMessage();
                }
        }

        // List<Show> shows = searchSvc.getTitlesByNameFromDb(search);

        // int showIndex = Integer.parseInt(form.getFirst("showIndex"));

        // Show show = shows.get(showIndex);

        // boolean saveFavourites = false;

        // if (form.containsKey("selected")) {
        //     saveFavourites = Boolean.valueOf(form.getFirst("selected"));
        //     try {
        //         showSvc.saveShow(username, password, show);
        //     } catch (Exception e) {
        //         e.getMessage();
        //     }
        // }

        // try {
        //     flag = showSvc.saveShow(username, password, show)
        // } catch(Exception e) {
        //     e.printStackTrace();
        // }

        mvc.setViewName("verified");
        mvc.addObject("username", username.substring(0, 1).toUpperCase() + username.substring(1));
        mvc.setStatus(HttpStatus.CREATED);

        return mvc;
        
    }

    @GetMapping(path="/favourites")
    public ModelAndView getFavourites(HttpSession session) {

        String username = (String) session.getAttribute("username");

        List<Show> savedShows = showSvc.getShowsByUsername(username);

        ModelAndView mvc = new ModelAndView();

        mvc.setViewName("favourites");
        mvc.addObject("username", username.substring(0, 1).toUpperCase() + username.substring(1));
        mvc.addObject("savedShows", savedShows);

        return mvc;

    }

}
