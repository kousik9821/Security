package com.example.WebSecurity.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.WebSecurity.entity.Article;
import com.example.WebSecurity.entity.User;
import com.example.WebSecurity.exception.RecordNotFoundException;
import com.example.WebSecurity.service.UserService;
@Controller
public class LoginController {
	 @Autowired
	    private UserService userService;

	    @RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
	    public ModelAndView login(){
	        ModelAndView modelAndView = new ModelAndView();
	        modelAndView.setViewName("login");
	        return modelAndView;
	    }


	    @RequestMapping(value="/registration", method = RequestMethod.GET)
	    public ModelAndView registration(){
	        ModelAndView modelAndView = new ModelAndView();
	        User user = new User();
	        modelAndView.addObject("user", user);
	        modelAndView.setViewName("registration");
	        return modelAndView;
	    }

	    @RequestMapping(value = "/registration", method = RequestMethod.POST)
	    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
	        ModelAndView modelAndView = new ModelAndView();
	        User userExists = userService.findUserByEmail(user.getEmail());
	        if (userExists != null) {
	            bindingResult
	                    .rejectValue("email", "error.user",
	                            "There is already a user registered with the email provided");
	        }
	        if (bindingResult.hasErrors()) {
	            modelAndView.setViewName("registration");
	        } else {
	            userService.saveUser(user);
	            modelAndView.addObject("successMessage", "User has been registered successfully");
	            modelAndView.addObject("user", new User());
	            modelAndView.setViewName("registration");

	        }
	        return modelAndView;
	    }

	    @RequestMapping(value="/admin/home", method = RequestMethod.GET)
	    public ModelAndView home(){
	        ModelAndView modelAndView = new ModelAndView();
	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        User user = userService.findUserByEmail(auth.getName());
	        modelAndView.addObject("userName", "Welcome " + user.getEmail()+ " " + user.getLastName() + " (" + user.getEmail() + ")"+user.getRoles());
	        modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
	        //modelAndView.setViewName("admin/home");
	        return modelAndView;
	    }
	    @RequestMapping(value="/getArticle", method = RequestMethod.GET)
	    public String getAllArticle(Model model) 
	    {
	        User form = new User();
	        model.addAttribute("personForm", form);
	     
	 
	        List<Article> list = userService.getAllArticles();
	 
	        model.addAttribute("employees", list);
	        return "list-employees";
	    }
	    @RequestMapping(path = {"/edit", "/edit/{id}"})
	    //@PreAuthorize("hasRole('USER')")
	    public String editEmployeeById(Model model, @PathVariable("id") Optional<Long> id) 
	                            throws RecordNotFoundException 
	    {
	        if (id.isPresent()) {
	            Article entity = userService.getArticleeById(id.get());
	            model.addAttribute("employee", entity);
	        } else {
	            model.addAttribute("employee", new Article());
	        }
	        return "add-edit-employee";
	    }
	    @RequestMapping(path = "/createEmployee", method = RequestMethod.POST)
	    public String createOrUpdateEmployee(Article employee) 
	    {
	        userService.createOrUpdateEmployee(employee);
	        return "redirect:/";
	    }
	     	
	 /*   @RequestMapping(value = { "/selectOptionExample1" }, method = RequestMethod.GET)
	    public String selectOptionExample1Page(Model model) {
	     

	     
	        return "list-employees";
	    }*/
	    
}
