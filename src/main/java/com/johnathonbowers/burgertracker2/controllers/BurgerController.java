package com.johnathonbowers.burgertracker2.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.johnathonbowers.burgertracker2.models.Burger;
import com.johnathonbowers.burgertracker2.services.BurgerService;

@Controller
public class BurgerController {

	@Autowired
	BurgerService burgerService;
	
	@GetMapping("/")
	public String getAll(@ModelAttribute("burger") Burger burger, Model model) {
		List<Burger> allBurgers = burgerService.allBurgers();
		model.addAttribute("allBurgers", allBurgers);
		return "index.jsp";
	}
	
	@PostMapping("/burgers/add")
	public String createBurger(@Valid @ModelAttribute("burger") Burger burger, BindingResult result, Model model) {
		if (result.hasErrors()) {
			List<Burger> allBurgers = burgerService.allBurgers();
			model.addAttribute("allBurgers", allBurgers);
			return "index.jsp";
		} else {
			burgerService.createBurger(burger);
			return "redirect:/";
		}
	}
	
	@GetMapping("/burgers/{id}/edit")
	public String editBurgerForm(@PathVariable("id") Long id, Model model) {
		Burger burger = burgerService.findBurger(id);
		model.addAttribute("burger", burger);
		return "editBookForm.jsp";
	}
	
	@PutMapping("/burgers/{id}")
	public String editBurger(@Valid @ModelAttribute("burger") Burger burger, BindingResult result) {
		if (result.hasErrors()) {
			return "editBookForm.jsp";
		} else {
			burgerService.updateBurger(burger);
			return "redirect:/";
		}
	}
	
}
