package com.prestamos.controllers;

import java.util.Arrays;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.prestamos.entities.PropuestaPrestamo;
import com.prestamos.entities.Solicitante;
import com.prestamos.entities.SolicitudPrestamo;

@Controller
public class PropuestaPrestamosController {
	int id;
	double montoURL;
	String plazoURL;
	@RequestMapping(value="/listadoPropuestas",method=RequestMethod.GET)
	public String listarPrestamos(Model model) {
		RestTemplate plantilla = new RestTemplate();
		String urlServicio = "http://localhost:8080/listarPropuestasPrestamos";
		System.out.println(urlServicio);
		ResponseEntity<Object[]> response = plantilla.getForEntity(
		urlServicio, Object[].class);
		model.addAttribute("propuestas",Arrays.asList(response.getBody()));
		return "ListadoPropuestas";
	}
	
	@RequestMapping(value="/listadoParaRegistrarPrestamo",method=RequestMethod.GET)
	public String listadoParaRegistrarPrestamos(Model model) {
		RestTemplate plantilla = new RestTemplate();
		String urlServicio = "http://localhost:8080/listarPropuestasPrestamosAprobados";
		System.out.println(urlServicio);
		ResponseEntity<Object[]> response = plantilla.getForEntity(
		urlServicio, Object[].class);
		model.addAttribute("propuestas",Arrays.asList(response.getBody()));
		return "ListadoPropuestasFinanciero";
	}
	
	@RequestMapping(value="/editarPropuesta/{idPropuesta}",method=RequestMethod.GET)
	public String mostrarPropuesta(@PathVariable int idPropuesta, Model model) {
		 model.addAttribute("idPropuesta",idPropuesta);
		  return "EditarPropuestaPrestamo";
	}

	@RequestMapping(value="/editarPropuesta/{idPropuesta}",method=RequestMethod.POST)
	public String actualizarPropuesta(@PathVariable String idPropuesta, @RequestParam String estado,Model model) {
		RestTemplate plantilla = new RestTemplate();
		System.out.println(estado);
		String resultado = plantilla.getForObject("http://localhost:8080/actualizarPropuesta/"+idPropuesta+"/"+estado+"/", String.class);
		String actualizarSolicitud = plantilla.getForObject("http://localhost:8080/actualizarEstadoSolicitud/"+id+"/"+estado, String.class);
		return "redirect:/listadoPropuestas";
	}
	
	@RequestMapping(value="/registrarPropuesta/{idSolicitud}/{monto}/{plazo}",method=RequestMethod.GET)
	public String mostrarRegistrarPropuesta(@PathVariable int idSolicitud,@PathVariable double monto,@PathVariable String plazo,Model model) {
		 id = idSolicitud;
		 montoURL = monto;
		 plazoURL = plazo;
		 RestTemplate plantilla = new RestTemplate();
		 SolicitudPrestamo solicitud = plantilla.getForObject("http://localhost:8080/devolverSolicitud/"+idSolicitud, SolicitudPrestamo.class);
		 String tasaInteres = plantilla.getForObject("http://localhost:8080/tasaInteresRecomendada/"+solicitud.getMotivo()+"/"+monto, String.class);
		 model.addAttribute("monto",monto);
		 model.addAttribute("plazo",plazo);
		 model.addAttribute("tasaInteres",tasaInteres);
		 return "RegistrarPropuestaPrestamos";
	}
	
	@RequestMapping(value="/registrarPropuesta/{idSolicitud}/{monto}/{plazo}",method=RequestMethod.POST)
	public String registrarPropuesta(@RequestParam String tasaInteres,@RequestParam String comentario, Model model) {
		  RestTemplate plantilla = new RestTemplate();
		  String urlServicio = "http://localhost:8080/registrarPropuestaPrestamo/"+id+"/"+montoURL+"/"+plazoURL+"/"+tasaInteres+"/"+comentario+"/Creada";
		  System.out.println(urlServicio);
		  plantilla.getForObject(urlServicio, String.class);
		  return "redirect:/listadoPropuestas";
	}
}
