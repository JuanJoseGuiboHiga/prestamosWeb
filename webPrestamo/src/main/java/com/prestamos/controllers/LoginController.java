package com.prestamos.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class LoginController {
	  @RequestMapping(value="/login",method=RequestMethod.GET)
	  public String login( Model model) {
			 return "login";
	  }
	  
	  @RequestMapping(value="/login",method=RequestMethod.POST)
	  public String logearse(@RequestParam String correo,@RequestParam String contrasenia, Model model) {
		  RestTemplate plantilla = new RestTemplate();
		  String urlServicio = "http://localhost:8080/autenticarUsario/"+correo+"/"+contrasenia;
		  String mensaje =plantilla.getForObject(urlServicio, String.class);
		  String urlRedireccionamiento = "http://localhost:8080/devolverUsuarioId/"+correo;
		  String idUsuario =plantilla.getForObject(urlRedireccionamiento, String.class);
		  String redireccionamiento = null;
		  if(idUsuario.equals("1"))
		  {
			  redireccionamiento = "RegistrarSolicitante";
		  }else if(idUsuario.equals("2")) {
			  redireccionamiento = "ListadoSolicitudes";
		  }else {
			  redireccionamiento = "ListadoPropuestas";
		  }
		  return redireccionamiento;
		
	  }
}
