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

import com.prestamos.entities.Cliente;
import com.prestamos.entities.Solicitante;
import com.prestamos.entities.SolicitudPrestamo;

@Controller
public class PrestamoController {
	  Solicitante cliente;
	  SolicitudPrestamo solicitudPrestamo;
	  static int propuesta;
	  @RequestMapping(value="/RegistrarPrestamo/{idPropuesta}",method=RequestMethod.GET)
	  public String listarDatosPrestamo(@PathVariable int idPropuesta, Model model) {
		 RestTemplate plantilla = new RestTemplate();
		 propuesta = idPropuesta;
		 String buscarSolicitanteURL = "http://localhost:8080/buscarSolicitante/"+idPropuesta;
		 String buscarSolicitudURL = "http://localhost:8080/buscarSolicitud/"+idPropuesta;
		 cliente = plantilla.getForObject(buscarSolicitanteURL, Solicitante.class);
		 solicitudPrestamo = plantilla.getForObject(buscarSolicitudURL, SolicitudPrestamo.class);
		 model.addAttribute("solicitud",solicitudPrestamo);
		 return "RegistrarPrestamo";
	  }
	  
	  @RequestMapping(value="/RegistrarPrestamo/{idPropuesta}",method=RequestMethod.POST)
	  public String registrarPrestamo( Model model) {
			 RestTemplate plantilla = new RestTemplate();
			 String urlregCliente = "http://localhost:8080/registrarCliente/"+cliente.getNombre()+"/"+cliente.getTipoDocumento()+"/"+cliente.getNumeroDocumento()+"/"+cliente.getCorreo()+"/"+cliente.getTelefono();
			 String idCliente = plantilla.getForObject(urlregCliente, String.class);
			 String urlServicio = "http://localhost:8080/registrarPrestamo/"+propuesta+"/"+Integer.parseInt(idCliente)+"/"+solicitudPrestamo.getMonto()+"/"+solicitudPrestamo.getMotivo();
			 plantilla.getForObject(urlServicio, int.class);
			 return "login";
	  }
	  
	  @RequestMapping(value="/listadoPrestamos",method=RequestMethod.GET)
	  public String listarPrestamos(Model model) {
			RestTemplate plantilla = new RestTemplate();
			String urlServicio = "http://localhost:8080/listadoPrestamos";
			System.out.println(urlServicio);
			ResponseEntity<Object[]> response = plantilla.getForEntity(urlServicio, Object[].class);
			model.addAttribute("prestamos",Arrays.asList(response.getBody()));
			return "ListarPrestamos";
	   }
}
