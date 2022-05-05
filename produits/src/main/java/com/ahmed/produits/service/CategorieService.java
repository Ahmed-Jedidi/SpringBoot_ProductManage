package com.ahmed.produits.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ahmed.produits.entities.Categorie;
import com.ahmed.produits.entities.Produit;

public interface CategorieService {
	
	Categorie saveCategorie(Categorie c);
	Categorie updateCategorie(Categorie c);
	void deleteCategoriet(Categorie c);
	void deleteCategorieById(Long id);
	Categorie getCategorie(Long id);
	List<Categorie> getAllCategories();

	
	Page<Categorie> getAllCategoriesParPage(int page, int size);

}
