package com.prestamos.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.prestamos.entities.RatiosFinancieros;
import com.prestamos.entities.Solicitante;
import com.prestamos.entities.SolicitudPrestamo;

@Controller
public class SolicitudPrestamoController {
	  Solicitante solicitante ;
	  SolicitudPrestamo solicitud;
	  @RequestMapping(value="/solicitudPrestamo",method=RequestMethod.GET)
	  public String mostrarSol(Model model) {
		  solicitante = (Solicitante)model.asMap().get("solicitante");
		  return "RegistrarSolicitudPrestamo";
	   }

	   @RequestMapping(value="/solicitudPrestamo",method=RequestMethod.POST)
	   public String regSol(@RequestParam String motivo,@RequestParam double monto,@RequestParam  String plazo,@RequestParam double activo,@RequestParam double pasivo,
				@RequestParam double patrimonio,@RequestParam double costo, @RequestParam double ventaTotal,  @RequestParam double gastosAdm,  @RequestParam double gastosVent,  @RequestParam double margenUti,
				@RequestParam("file") MultipartFile file, Model model) {
			  try {
				  String ruta = "C:\\Users\\JuanJosé\\Documents\\pdf\\"+file.getName()+".pdf";
				  file.transferTo(new File(ruta));
				  RestTemplate plantilla = new RestTemplate();
				  String pdf = file.getName()+".pdf";
				  String urlServicio = "http://localhost:8080/registrarSolPres/"+solicitante.getIdSolicitante()+"/"+motivo+"/"+monto+"/"+plazo+"/"+activo+"/"+pasivo+"/"+patrimonio+"/"+costo+"/"+ventaTotal+"/"+gastosAdm+"/"+gastosVent+"/"+margenUti+"/"+pdf+"/";
				  plantilla.getForObject(urlServicio, String.class);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  return "registroExitoso";
		}
	    
		@RequestMapping(value="/listadoSolicitudes",method=RequestMethod.GET)
		public String listarPrestamos(Model model) {
			RestTemplate plantilla = new RestTemplate();
			String urlServicio = "http://localhost:8080/listadoSolicitudes";
			System.out.println(urlServicio);
			ResponseEntity<Object[]> response = plantilla.getForEntity(urlServicio, Object[].class);
			model.addAttribute("solicitudes",Arrays.asList(response.getBody()));
			return "ListadoSolicitudes";
		  }
		
    	@RequestMapping(value="/listadoSolicitudes",method=RequestMethod.POST)
		public String generarPropuesta (RedirectAttributes redirectAttributes,Model model) {
			SolicitudPrestamo solicitud = (SolicitudPrestamo)model.asMap().get("solicitante");
			redirectAttributes.addFlashAttribute("solicitud", solicitante);
			return "redirect:/registrarPropuesta/1/100/1";
		  }
    	
    	@RequestMapping(value="/mostrarSolicitud/{idSolicitud}/{activo}/{pasivo}/{patrimonio}/{costo}/{ventaTotal}/{gastosAdm}/{gastosVent}/{margenUti}/{pdf}",method=RequestMethod.GET)
		public String mostrarSolicitud (@PathVariable int idSolicitud,@PathVariable double activo,
				@PathVariable double pasivo,@PathVariable double patrimonio,@PathVariable double costo,@PathVariable double ventaTotal,@PathVariable double gastosAdm,@PathVariable double gastosVent, 
				@PathVariable double margenUti,@PathVariable String pdf,@PathVariable double monto,@PathVariable String plazo, Model model) {
    		RestTemplate plantilla = new RestTemplate();
			String urlRatios = "http://localhost:8080/generarRatios/"+activo+"/"+pasivo+"/"+patrimonio+"/"+costo+"/"+ventaTotal+"/"+gastosAdm+"/"+gastosVent+"/"+margenUti;
			System.out.println(urlRatios);
			RatiosFinancieros ratios = plantilla.getForObject(urlRatios, RatiosFinancieros.class);
			model.addAttribute("ratios",ratios);
			model.addAttribute("pdf","ftp://localhost/"+pdf);
			String urlArbol = "http://localhost:8080/arbolDecision/"+monto+"/"+plazo+"/"+activo+"/"+pasivo+"/"+patrimonio+"/"+costo+"/"+ventaTotal+"/"+gastosAdm+"/"+gastosVent+"/"+margenUti;
			String arbol = plantilla.getForObject(urlArbol, String.class);
			model.addAttribute("arbol",arbol);
			return "MostrarSolicitudPrestamo";
		  }
    	
    	@RequestMapping(value="/mostrarSolicitud/{idSolicitud}/{activo}/{pasivo}/{patrimonio}/{costo}/{ventaTotal}/{gastosAdm}/{gastosVent}/{margenUti}",method=RequestMethod.POST)
		public String retornarMostrarSolicitud (Model model) {
			return "ListadoSolicitudes";
		  }
}
