package controllers;

import models.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.UserService;


@Controller
public class UserController {

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView allUsers() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userPages/users");
        modelAndView.addObject("users", UserService.findAll() );
        return modelAndView;
    }

    @RequestMapping(value = "/users/addUser", method = RequestMethod.GET)
    public ModelAndView addUserPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userPages/addUser");
        return modelAndView;
    }

    @RequestMapping(value = "/users/addUser", method = RequestMethod.POST)
    public ModelAndView addUser(@ModelAttribute("user") User user) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/users");
        UserService.save(user);
        return modelAndView;
    }

    @RequestMapping(value = "/users/editUser/{id}", method = RequestMethod.GET)
    public ModelAndView editUserPage(@PathVariable("id") int id) {
        User user = UserService.findById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userPages/editUser");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @RequestMapping(value = "/users/editUser", method = RequestMethod.POST)
    public ModelAndView editUser(@ModelAttribute("user") User user) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/users");
        UserService.update(user);
        return modelAndView;
    }

    @RequestMapping(value = "/users/deleteUser/{id}", method = RequestMethod.GET)
    public ModelAndView deleteUserPage(@PathVariable("id") int id) {
        UserService.delete(UserService.findById(id));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/users");
        return modelAndView;
    }

}
