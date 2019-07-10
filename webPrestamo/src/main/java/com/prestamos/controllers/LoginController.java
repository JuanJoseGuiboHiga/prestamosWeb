package com.prestamos.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

@Controller
public class LoginController {
	  @RequestMapping(value="/",method=RequestMethod.GET)
	  public String login( Model model) {
			 return "login";
	  }
}
