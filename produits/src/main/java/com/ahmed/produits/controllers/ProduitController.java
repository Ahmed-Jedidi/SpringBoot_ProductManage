package com.ahmed.produits.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
//import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

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
	
	List<Categorie> cats = categorieService.getAllCategories();
	modelMap.addAttribute("categories", cats);

	modelMap.addAttribute("pages", new int[prods.getTotalPages()]);

	modelMap.addAttribute("currentPage", page);
	return "listeProduits";
	}
	
	/////////////////////////////////////////////////////////////////////////
	@RequestMapping("/rechercherProduit")
	public String rechercherProduit(ModelMap modelMap,
			@RequestParam (name="page",defaultValue = "0") int page,
			@RequestParam (name="size", defaultValue = "6") int size,
			@RequestParam("nomProd") String nomProd,
			@PageableDefault(page = 0, size = 6) Pageable pageable)

	{
	
	List<Produit> pros = produitService.findByNomProduitContains(nomProd);
	
	int start = (int) pageable.getOffset();
	int end = (int) ((start + pageable.getPageSize()) > pros.size() ? pros.size()
					: (start + pageable.getPageSize()));
	Page<Produit> prods = new PageImpl<Produit>(pros.subList(start, end), pageable, pros.size());
	
	//Ou bien 2eme methode
	//PagedListHolder<Produit> prods = new PagedListHolder(pros);
	//prods.setPageSize(10); // number of items per page
	//prods.setPage(0);

	
	// list of items on this page .getContent()
	modelMap.addAttribute("produits", prods.getContent());
	

	modelMap.addAttribute("pages", new int[prods.getTotalPages()]);

	modelMap.addAttribute("currentPage", page);
	modelMap.addAttribute("nomProd", nomProd);
	modelMap.addAttribute("mode", "nom");
	return "recherche";
	}
	/////////////////////////////////////////////////////////////////////////
	
	@RequestMapping("/rechercherProduitByCatId")
	public String rechercherProduitByCatId(ModelMap modelMap,
			@RequestParam (name="page",defaultValue = "0") int page,
			@RequestParam (name="size", defaultValue = "6") int size,
			@RequestParam("id") Long id ,
			@PageableDefault(page = 0, size = 6) Pageable pageable)

	{
		
		List<Categorie> cats = categorieService.getAllCategories();
		modelMap.addAttribute("categories", cats);
		
		List<Produit> pros = produitService.findByCategorieIdCat(id);
		int start = (int) pageable.getOffset();
		int end = (int) ((start + pageable.getPageSize()) > pros.size() ? pros.size()
						: (start + pageable.getPageSize()));
		Page<Produit> prods = new PageImpl<Produit>(pros.subList(start, end), pageable, pros.size());
		modelMap.addAttribute("produits", prods.getContent());
		

		modelMap.addAttribute("pages", new int[prods.getTotalPages()]);

		modelMap.addAttribute("currentPage", page);
		modelMap.addAttribute("id", id);
		modelMap.addAttribute("mode", "id");
		return "recherche";
	}
	
	
	
	
	/////////////////////////////////////////////////////////////////////////
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
		List<Categorie> cats= categorieService.getAllCategories();
		Produit p = new Produit();
		Categorie cat = new Categorie(0L,"Selectionnez","",null) ;
		p.setCategorie(cat);
		modelMap.addAttribute("produit", p);
		
		//cats.add(0, cat);
		modelMap.addAttribute("categories", cats);
		
		
		modelMap.addAttribute("mode", "new");
		return "formProduit";
	}
	
	@RequestMapping("/modifierProduit")
	public String editerProduit(@RequestParam("id") Long id, ModelMap modelMap) {
		
		
		Categorie c = categorieService.getCategorie(produitService.getProduit(id).getCategorie().getIdCat());
		modelMap.addAttribute("categorie", c);
		
		Produit p = produitService.getProduit(id);
		modelMap.addAttribute("produit", p);
		
		List<Categorie> cats= categorieService.getAllCategories();
		modelMap.addAttribute("categories", cats);
		
		modelMap.addAttribute("mode", "edit");
		return "formProduit";
	}
	
	
	

	
	@RequestMapping("/saveProduit")
	public String saveProduit(@Valid Produit produit ,BindingResult bindingResult/*, long idc*/ /*, ModelMap modelMap*/)

	{
		/*System.out.println(produit);
		if (bindingResult.hasErrors() || (produit.getCategorie().getIdCat() == 0) )
			return "formProduit";
		//Categorie ccc = categorieService.getCategorie(idc) ;
		//produit.setCategorie(ccc);
		else {
			produitService.saveProduit(produit);
			return "redirect:/ListeProduits";
		}*/
		
		/*int page = 0;
		int size = 2;
		Page<Produit> prods = produitService.getAllProduitsParPage(page, size);
		modelMap.addAttribute("produits", prods);
		modelMap.addAttribute("pages", new int[prods.getTotalPages()]);
		modelMap.addAttribute("currentPage", page);
		modelMap.addAttribute("size", size);
		return "listeProduits";*/
		if (bindingResult.hasErrors() )
			return "formProduit";
		
		produitService.saveProduit(produit);
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