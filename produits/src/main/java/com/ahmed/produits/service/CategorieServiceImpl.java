package com.ahmed.produits.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ahmed.produits.entities.Categorie;
import com.ahmed.produits.entities.Produit;
import com.ahmed.produits.repos.CategorieRepository;

@Service
public class CategorieServiceImpl implements CategorieService {
	
	@Autowired
	CategorieRepository categorieRepository;


	@Override
	public Categorie saveCategorie(Categorie c) {
		return categorieRepository.save(c);
	}

	@Override
	public Categorie updateCategorie(Categorie c) {
		return categorieRepository.save(c);
	}

	@Override
	public void deleteCategoriet(Categorie c) {
		categorieRepository.delete(c);
	}

	@Override
	public void deleteCategorieById(Long id) {
		categorieRepository.deleteById(id);
		
	}

	@Override
	public Categorie getCategorie(Long id) {
		return categorieRepository.findById(id).get();
	}

	@Override
	public List<Categorie> getAllCategories() {
		return categorieRepository.findAll();
	}

	@Override
	public Page<Categorie> getAllCategoriesParPage(int page, int size) {
		return categorieRepository.findAll(PageRequest.of(page, size));
	}
}