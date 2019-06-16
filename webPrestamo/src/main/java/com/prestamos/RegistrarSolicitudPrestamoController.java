package com.prestamos;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
@Controller
public class RegistrarSolicitudPrestamoController {
	
	  @RequestMapping(value="/solicitudPrestamo",method=RequestMethod.GET)
		public String mostrarSol(Model model) {
		  return "RegistrarPrestamo";
		}

	    @RequestMapping(value="/solicitudPrestamo",method=RequestMethod.POST)
		public String regSol(@RequestParam int idSolicitante, @RequestParam String motivo,@RequestParam double monto,@RequestParam double activo,@RequestParam double pasivo,
				@RequestParam double patrimonio,@RequestParam double costo, @RequestParam double ventaTotal,  @RequestParam double gastosAdm,  @RequestParam double gastosVent,  @RequestParam double margenUti,
							 Model model) {
			  RestTemplate plantilla = new RestTemplate();
			  String resultado = plantilla.getForObject("http://localhost:8080/solPrestamo/"+idSolicitante+"/"+motivo+"/"+monto+"/"+activo+"/"+pasivo+"/"+patrimonio+"/"+costo+"/"+ventaTotal+"/"+gastosAdm+"/"+gastosVent+"/"+margenUti+"/", String.class);
			  model.addAttribute("mensaje",resultado);
			  return "ListadoSolicitudes";
		}
}
