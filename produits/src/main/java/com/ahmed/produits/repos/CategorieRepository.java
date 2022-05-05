package com.ahmed.produits.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.ahmed.produits.entities.Categorie;

@RepositoryRestResource(path = "restc")
public interface CategorieRepository extends JpaRepository<Categorie, Long> {

}
