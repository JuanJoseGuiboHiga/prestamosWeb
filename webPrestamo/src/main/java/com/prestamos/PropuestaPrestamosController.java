package com.prestamos;

import java.util.Arrays;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
@Controller
public class PropuestaPrestamosController {
	  @RequestMapping(value="/listadoPropuestas",method=RequestMethod.GET)
			public String listarPrestamos(Model model) {
					  RestTemplate plantilla = new RestTemplate();
					  String urlServicio = "http://localhost:8080/listadoPropuestas";
					  System.out.println(urlServicio);
					  ResponseEntity<Object[]> response = plantilla.getForEntity(
							  urlServicio, Object[].class);
					  model.addAttribute("propuestas",Arrays.asList(response.getBody()));
				  return "ListadoPropuestas";
			}
}
