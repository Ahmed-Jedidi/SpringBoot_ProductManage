package com.ahmed.produits.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ahmed.produits.entities.Categorie;
import com.ahmed.produits.entities.Produit;
import com.ahmed.produits.service.CategorieService;
import com.ahmed.produits.service.ProduitService;

@Controller
public class ProduitController {
	@Autowired
	ProduitService produitService;
	
	@Autowired
	CategorieService categorieService;
	
	
	@RequestMapping("/ListeProduits")
	public String listeProduits(ModelMap modelMap,

	@RequestParam (name="page",defaultValue = "0") int page,
	@RequestParam (name="size", defaultValue = "6") int size)

	{
	Page<Produit> prods = produitService.getAllProduitsParPage(page, size);
	modelMap.addAttribute("produits", prods);

	modelMap.addAttribute("pages", new int[prods.getTotalPages()]);

	modelMap.addAttribute("currentPage", page);
	return "listeProduits";
	}
	
	
	
	@RequestMapping("/supprimerProduit")
	public String supprimerProduit(@RequestParam("id") Long id,

	ModelMap modelMap,
	@RequestParam (name="page",defaultValue = "0") int page,
	@RequestParam (name="size", defaultValue = "6") int size)

	{
	produitService.deleteProduitById(id);
	Page<Produit> prods = produitService.getAllProduitsParPage(page, size);

	modelMap.addAttribute("produits", prods);
	modelMap.addAttribute("pages", new int[prods.getTotalPages()]);
	modelMap.addAttribute("currentPage", page);
	modelMap.addAttribute("size", size);
	return "listeProduits";
	}
	
	
	
	
	
	@RequestMapping("/showCreate")
	public String showCreate(ModelMap modelMap) {
		modelMap.addAttribute("produit", new Produit());
		
		List<Categorie> cats= categorieService.getAllCategories();
		modelMap.addAttribute("categories", cats);
		
		modelMap.addAttribute("mode", "new");
		return "formProduit";
	}
	
	@RequestMapping("/modifierProduit")
	public String editerProduit(@RequestParam("id") Long id, ModelMap modelMap/*, @RequestParam(name = "idc", defaultValue = "1") int idc*/) {
		
		//Genre c = categorieService.getCategorie(livreService.getLivre(id).getGenre().getIdGen());
		Categorie c = categorieService.getCategorie(produitService.getProduit(id).getCategorie().getIdCat());
		modelMap.addAttribute("categorie", c);
		
		Produit p = produitService.getProduit(id);
		modelMap.addAttribute("produit", p);
		
		List<Categorie> cats= categorieService.getAllCategories();
		modelMap.addAttribute("categories", cats);
		
		long idc = 1 ;
		modelMap.addAttribute("idc", idc);
		/*Long idca = p.getCategorie().getIdCat();
		modelMap.addAttribute("idc", idca);*/
		
		modelMap.addAttribute("mode", "edit");
		return "formProduit";
	}
	
	
	

	
	@RequestMapping("/saveProduit")
	public String saveProduit(@Valid Produit produit ,BindingResult bindingResult, long idc /*, ModelMap modelMap*/)

	{
		if (bindingResult.hasErrors())
			return "formProduit";
		Categorie ccc = categorieService.getCategorie(idc) ;
		produit.setCategorie(ccc);
		produitService.saveProduit(produit);
		
		/*int page = 0;
		int size = 2;
		Page<Produit> prods = produitService.getAllProduitsParPage(page, size);
		modelMap.addAttribute("produits", prods);
		modelMap.addAttribute("pages", new int[prods.getTotalPages()]);
		modelMap.addAttribute("currentPage", page);
		modelMap.addAttribute("size", size);
		return "listeProduits";*/
		
		
		return "redirect:/ListeProduits";
		}
		
	
	
	/*@RequestMapping("/saveProduit")
	public String saveProduit(@ModelAttribute("produit") Produit produit,
								@RequestParam("date") String date,
								ModelMap modelMap) throws ParseException {
		//conversion de la date

		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		Date dateCreation = dateformat.parse(String.valueOf(date));
		produit.setDateCreation(dateCreation);

		Produit saveProduit = produitService.saveProduit(produit);
		String msg = "produit enregistré avec Id " + saveProduit.getIdProduit();
		modelMap.addAttribute("msg", msg);
		return "createProduit";
	}*/
	
	/*@RequestMapping("/ListeProduits")
	public String listeProduits(ModelMap modelMap) {
		List<Produit> prods = produitService.getAllProduits();
		modelMap.addAttribute("produits", prods);
		return "listeProduits";
	}*/

	/*@RequestMapping("/supprimerProduit")
	public String supprimerProduit(@RequestParam("id") Long id, ModelMap modelMap) {
		produitService.deleteProduitById(id);
		List<Produit> prods = produitService.getAllProduits();
		modelMap.addAttribute("produits", prods);
		return "listeProduits";
	}*/
	
	



	/*@RequestMapping("/updateProduit")
	public String updateProduit(@ModelAttribute("produit") Produit produit, 
								@RequestParam("date") String date,
								ModelMap modelMap) throws ParseException

	{
		// conversion de la date

		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		Date dateCreation = dateformat.parse(String.valueOf(date));
		produit.setDateCreation(dateCreation);
		produitService.updateProduit(produit);
		List<Produit> prods = produitService.getAllProduits();
		modelMap.addAttribute("produits", prods);
		return "listeProduits";

	}*/
}