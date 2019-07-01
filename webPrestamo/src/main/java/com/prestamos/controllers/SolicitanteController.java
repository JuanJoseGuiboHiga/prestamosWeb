package com.prestamos.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.prestamos.entities.Solicitante;

@Controller
public class SolicitanteController {
	  @RequestMapping(value="/registrarSolicitante",method=RequestMethod.GET)
	  public String mostrarFormulario(Model model) {
		  return "RegistrarSolicitante";
	   }
	  
	   @RequestMapping(value="/registrarSolicitante",method=RequestMethod.POST)
	   public String regSol(@RequestParam String nombre,@RequestParam String tipoDocumento,@RequestParam String numDoc,@RequestParam String correo,
				@RequestParam int telefono, Model model,RedirectAttributes redirectAttributes) {
			  try {
				  RestTemplate plantilla = new RestTemplate();
				  String urlServicio = "http://localhost:8080/registrarSolicitante/"+nombre+"/"+tipoDocumento+"/"+numDoc+"/"+correo+"/"+telefono;
				  System.out.println(urlServicio);
				  Solicitante solicitante = new Solicitante();
				  solicitante.setNombre(nombre);
				  solicitante.setTipoDocumento(tipoDocumento);
				  solicitante.setNumeroDocumento(numDoc);
				  solicitante.setCorreo(correo);
				  solicitante.setTelefono(telefono);
				  String idSolicitante =plantilla.getForObject(urlServicio, String.class);
				  solicitante.setIdSolicitante(Integer.parseInt(idSolicitante));
				  redirectAttributes.addFlashAttribute("solicitante", solicitante);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
		}
	      return "redirect:/solicitudPrestamo";
	   }


}
