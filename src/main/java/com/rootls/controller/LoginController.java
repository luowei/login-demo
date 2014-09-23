package com.rootls.controller;

import com.rootls.domain.User;
import com.rootls.service.UserService;
import com.rootls.tools.HashedPasswordWithSalt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller("loginController")
public class LoginController {
  @Autowired
  private UserService userService;
  protected final Logger log = LoggerFactory.getLogger(LoginController.class);

  @RequestMapping(value = "/signup", method = RequestMethod.GET)
  protected void signup(Model model) throws Exception {
    model.addAttribute("user", new User());
  }

  @RequestMapping(value = "/signup", method = RequestMethod.POST)
  protected String signupPost(Model model, @Valid @ModelAttribute User user, BindingResult bindingResult) {
      if (bindingResult.hasErrors()) {
        return "signup";
      }
      List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
      roles.add(new SimpleGrantedAuthority("ROLE_USER"));
      User realUser = new User(user.getUsername(), HashedPasswordWithSalt.getHashedPassword(user), user.getEmail(), roles);

      userService.add(realUser);
      return "redirect:/";
  }
  
  @RequestMapping(value = "/login", method = RequestMethod.GET)
  protected void login(Model model) throws Exception {
    model.addAttribute("user", new User());
  }

  public void setUserService(UserService userService) {
    this.userService = userService;
  }

}