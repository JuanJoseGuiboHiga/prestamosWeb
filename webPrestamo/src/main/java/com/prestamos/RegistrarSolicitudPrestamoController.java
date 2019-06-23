package com.prestamos;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
@Controller
public class RegistrarSolicitudPrestamoController {
	
	  @RequestMapping(value="/solicitudPrestamo",method=RequestMethod.GET)
		public String mostrarSol(Model model) {
		  return "RegistrarSolicitudPrestamo";
		}

	    @RequestMapping(value="/solicitudPrestamo",method=RequestMethod.POST)
		public String regSol(@RequestParam int idSolicitante, @RequestParam String motivo,@RequestParam double monto,@RequestParam double activo,@RequestParam double pasivo,
				@RequestParam double patrimonio,@RequestParam double costo, @RequestParam double ventaTotal,  @RequestParam double gastosAdm,  @RequestParam double gastosVent,  @RequestParam double margenUti,
				@RequestParam("file") MultipartFile file, Model model) {
			  try {
				  String ruta = "C:\\Users\\JuanJosé\\Documents\\pdf\\"+file.getName()+".pdf";
				  file.transferTo(new File(ruta));
				  RestTemplate plantilla = new RestTemplate();
				  String pdf = URLEncoder.encode(ruta,"UTF-8");
				  String urlServicio = "http://localhost:8080/solPrestamo/"+idSolicitante+"/"+motivo+"/"+monto+"/"+activo+"/"+pasivo+"/"+patrimonio+"/"+costo+"/"+ventaTotal+"/"+gastosAdm+"/"+gastosVent+"/"+margenUti+"/"+pdf+"/";
				  System.out.println(urlServicio);
				  String resultado = plantilla.getForObject("http://localhost:8080/solPrestamo/"+idSolicitante+"/"+motivo+"/"+monto+"/"+activo+"/"+pasivo+"/"+patrimonio+"/"+costo+"/"+ventaTotal+"/"+gastosAdm+"/"+gastosVent+"/"+margenUti+"/"+pdf+"/", String.class);
				  model.addAttribute("mensaje",resultado);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  return "ListadoSolicitudes";
		}
	    
		  @RequestMapping(value="/listadoSolicitudes",method=RequestMethod.GET)
				public String listarPrestamos(Model model) {
						  RestTemplate plantilla = new RestTemplate();
						  String urlServicio = "http://localhost:8080/listadoSolicitudes";
						  System.out.println(urlServicio);
						  ResponseEntity<Object[]> response = plantilla.getForEntity(
								  urlServicio, Object[].class);
						  model.addAttribute("solicitudes",Arrays.asList(response.getBody()));
					  return "ListadoSolicitudes";
				}
}
