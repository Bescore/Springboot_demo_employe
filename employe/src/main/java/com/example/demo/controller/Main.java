package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.modele.Employe;
import com.example.demo.repository.EmployeRepository;



@Controller
public class Main {
	@Autowired
	private EmployeRepository EmpRepo;

	@GetMapping("/home")
	public String Home(Employe employe,Model model) {

		fonctionReadHome( model);
		return "home";
	}
	
	@PostMapping("/home")
	public String Homepost(Model model,@Validated Employe employe, BindingResult bindingResult) {
		fonctionReadHome( model);

		if (bindingResult.hasErrors()){
			return "home";
		}
		if (EmpRepo.findByEmail(employe.getEmail())==null){
			
			EmpRepo.save(employe);
		}else {
			bindingResult.addError(new FieldError("user","email","mauvais mail"));
		}
		return "redirect:/home";
	}
	
	@GetMapping("/show/{id}")
	public String show(@PathVariable(value="id")Long employeId, Model model) {
		
		Employe employe=null;
		
		Optional<Employe> employe_infos=EmpRepo.findById(employeId);
		
		if(employe_infos.isPresent()) {
			employe=employe_infos.get();
			
			model.addAttribute("employe",employe);
		}
		
		return "show";
	}
	@PostMapping("/show/{id}")
	public String update(@PathVariable(value="id")Long EmployeId,@Validated Employe employe, Model model /*on utilise pas de modèle*/) {
		
		
		
		//version 1
		Optional<Employe> Emp=EmpRepo.findById(EmployeId);
		Employe Employe2=null;
		if(Emp.isPresent()) {
		
			
			Employe2=Emp.get();

			Employe2.setNom(employe.getNom());
			Employe2.setPrenom(employe.getPrenom());
			Employe2.setEmail(employe.getEmail());
			Employe2.setFonction(employe.getFonction());
			
			EmpRepo.save(Employe2);
		}
		return "redirect:/home";
	}
	
	@GetMapping("/delete/{id}")
	public String supprimer(@PathVariable(value="id") Long EmployeId, Model model /*on utilise pas le modèle ici*/) {
		
		Employe employe=null;
		
		Optional<Employe> user=EmpRepo.findById(EmployeId);
		
		if(user.isPresent()) {
			
			employe=user.get();
	;		EmpRepo.delete(employe);
		}
		
		return "redirect:/home";
	}
	
	
	
	public String fonctionReadHome(Model model) {
		
		List<Employe> employes = EmpRepo.findAll();

		model.addAttribute("employes", employes);
		return "home";
	}
	
	
}
