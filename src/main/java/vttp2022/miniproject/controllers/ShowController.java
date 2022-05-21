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
public class ShowController {

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
        
        System.out.println(">>>>> Search Term: " + search);

        String username = (String) session.getAttribute("username");

        ModelAndView mvc = new ModelAndView();

        try {
            List<Show> shows = searchSvc.getTitlesByNameFromDb(search);
            mvc.addObject("shows", shows);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mvc.setViewName("result");
        mvc.setStatus(HttpStatus.OK);
        mvc.addObject("username", username.substring(0, 1).toUpperCase() + username.substring(1));
        mvc.addObject("search_name", search);

        return mvc;
    }

    @PostMapping(path="/saved", consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView postSaved(@RequestBody MultiValueMap<String, String> form, HttpSession session) {

        String username = (String) session.getAttribute("username");

        List<String> listOfIds = form.get("selected");

        System.out.println(">>>>> listOfIds: " + listOfIds);

        if (!listOfIds.isEmpty()) {
            for (int i=0; i<listOfIds.size(); i++) {
                Integer titleId = Integer.parseInt(listOfIds.get(i));
                Show show = searchSvc.getDetailsByIdFromDb(titleId);

                try {
                    boolean saved = showSvc.saveShow(username, show);
                    System.out.println(">>>>> saved: " + saved);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        ModelAndView mvc = new ModelAndView();

        mvc.setViewName("verified");
        mvc.addObject("username", username.substring(0, 1).toUpperCase() + username.substring(1));
        mvc.addObject("message", "Show(s) saved. You may find them under 'Saved Favourites'.");
        mvc.setStatus(HttpStatus.CREATED);

        return mvc;
    }

    @GetMapping(path="/favourites")
    public ModelAndView getFavourites(HttpSession session) {

        String username = (String) session.getAttribute("username");

        List<Show> savedShows = showSvc.getAllShowsByUsername(username);

        ModelAndView mvc = new ModelAndView();

        mvc.setViewName("favourites");
        mvc.addObject("username", username.substring(0, 1).toUpperCase() + username.substring(1));
        mvc.addObject("savedShows", savedShows);

        return mvc;
    }

    @PostMapping(path="/favourites", consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView postFavourites(@RequestBody MultiValueMap<String, String> form, HttpSession session) {

        String username = (String) session.getAttribute("username");

        ModelAndView mvc = new ModelAndView();

        List<String> listOfIds = form.get("selected");

        System.out.println(">>>>> listOfIds: " + listOfIds);

        if (!listOfIds.isEmpty()) {
            for (int i=0; i<listOfIds.size(); i++) {
                Integer titleId = Integer.parseInt(listOfIds.get(i));

                try {
                    boolean removed = showSvc.deleteShow(username, titleId);
                    System.out.println(">>>>> removed: " + removed);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } 

        mvc.setViewName("verified");
        mvc.addObject("username", username.substring(0, 1).toUpperCase() + username.substring(1));

        return mvc;
    }

}
