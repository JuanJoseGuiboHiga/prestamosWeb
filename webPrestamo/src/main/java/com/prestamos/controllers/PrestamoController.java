package com.prestamos.controllers;

import java.util.Arrays;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.prestamos.entities.Cliente;
import com.prestamos.entities.Solicitante;

@Controller
public class PrestamoController {
	  @RequestMapping(value="/RegistrarPrestamo",method=RequestMethod.POST)
	  public String registrarPrestamo(@RequestParam int idPropuesta, @RequestParam String idCliente,@RequestParam double monto,@RequestParam String motivo,@RequestParam String estado, Model model) {
		 RestTemplate plantilla = new RestTemplate();
		 String urlServicio = "http://localhost:8080/registrarPrestamo/"+idPropuesta+"/"+idCliente+"/"+monto+"/"+motivo+"/"+estado+"/";
		 Solicitante cliente = new Solicitante();
		 String urlregCliente = "http://localhost:8080/registrarSolicitante/"+cliente.getNombre()+"/"+cliente.getTipoDocumento()+"/"+cliente.getNumeroDocumento()+"/"+cliente.getCorreo()+"/"+cliente.getTelefono();
		 System.out.println(urlServicio);
		 plantilla.getForObject(urlregCliente, String.class);
		 plantilla.getForObject(urlServicio, String.class);
		 return "RegistrarPrestamo";
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
