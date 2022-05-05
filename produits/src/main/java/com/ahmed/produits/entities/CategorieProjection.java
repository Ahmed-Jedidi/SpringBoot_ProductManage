package com.ahmed.produits.entities;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "nomCat", types = { Categorie.class })
public interface CategorieProjection {
	public String getNomCat();
}