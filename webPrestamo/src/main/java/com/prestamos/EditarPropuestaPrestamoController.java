package com.prestamos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
@Controller
public class EditarPropuestaPrestamoController {

	  @RequestMapping(value="/editarPropuesta",method=RequestMethod.GET)
		public String mostrarPropuesta(Model model) {
		  return "EditarPropuestaPrestamo";
		}

	    @RequestMapping(value="/editarPropuesta",method=RequestMethod.POST)
		public String developersAdd(@RequestParam String idPropuesta, @RequestParam String estado,
							 Model model) {
			  RestTemplate plantilla = new RestTemplate();
			  String resultado = plantilla.getForObject("http://localhost:8080/cambiarEstadoSolicitud/"+idPropuesta+"/"+estado, String.class);
			  model.addAttribute("mensaje",resultado);
			  return "ListadoPropuestas";
		}

}
