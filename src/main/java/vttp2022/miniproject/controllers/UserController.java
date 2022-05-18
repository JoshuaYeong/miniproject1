package vttp2022.miniproject.controllers;

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
import org.springframework.web.servlet.ModelAndView;

import vttp2022.miniproject.services.UserService;

@Controller
@RequestMapping
public class UserController {

    @Autowired
    private UserService userSvc;

    @GetMapping(path="/")
    public ModelAndView getHome() {

        ModelAndView mvc = new ModelAndView();
        
        mvc.setViewName("index");

        return mvc;
    }

    @PostMapping(path="/newuser", consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView postNewUser(@RequestBody MultiValueMap<String, String> form) {

        String username = form.getFirst("username");
        String password = form.getFirst("password");

        boolean success = false;

        try{
            success = userSvc.createNewUser(username, password);
        } catch(Exception e) {
            e.printStackTrace();
        }

        ModelAndView mvc = new ModelAndView();

        mvc.setViewName("newuser");

        if(!success) {
            mvc.addObject("message", "Error! User not created!");
            mvc.setStatus(HttpStatus.BAD_REQUEST);
        } else {
            mvc.addObject("message", "New user successfully created. You may proceed to login.");
            mvc.setStatus(HttpStatus.CREATED);
        }

        return mvc;
    }

    @GetMapping(path="/newuser")
    public ModelAndView getNewUser() {

        ModelAndView mvc = new ModelAndView();

        mvc.setViewName("newuser");

        return mvc;
    }

    @PostMapping(path="/", consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView postLogin(@RequestBody MultiValueMap<String, String> form, HttpSession session) {

        String username = form.getFirst("username");
        String password = form.getFirst("password");

        ModelAndView mvc = new ModelAndView();

        if (!userSvc.verifyUsername(username, password)) {
            mvc.setViewName("error");
            mvc.addObject("errMessage", "User not found!");
            mvc.setStatus(HttpStatus.UNAUTHORIZED);
        } else {
            session.setAttribute("username", username);
            mvc.setViewName("verified");
            mvc.addObject("username", username.substring(0, 1).toUpperCase() + username.substring(1));
            mvc.addObject("sucMessage", "Login successfully!");
            mvc.setStatus(HttpStatus.OK);
        }
        return mvc;
    }

    @GetMapping(path="/logout")
    public ModelAndView getLogout(HttpSession session) {
        
        ModelAndView mvc = new ModelAndView();

        session.invalidate();
        mvc.setViewName("index");
        
        return mvc;
    }

}
