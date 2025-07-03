package org.bea.backend.service;

import org.bea.backend.exception.IdNotFoundException;
import org.bea.backend.model.Recipe;
import org.bea.backend.model.RecipeDto;
import org.bea.backend.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final ServiceId serviceId;

    public RecipeService(RecipeRepository recipeRepository, ServiceId serviceId) {
        this.recipeRepository = recipeRepository;
        this.serviceId = serviceId;
    }

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public Optional<Recipe> findRecipeById(String id) {
        return recipeRepository.findById(id);
    }

    public Recipe getRecipeById(String id) {
        return findRecipeById(id).orElseThrow(() -> new IdNotFoundException("Recipe with Id: "+ id +" not found"));
    }

    public Recipe addRecipe(RecipeDto recipeDto) {
        Recipe newRecipe = new Recipe(
                serviceId.generateId(),
                recipeDto.title(),
                serviceId.generateSlug(recipeDto.title()),
                recipeDto.recipeIngredients()
        );
        recipeRepository.save(newRecipe);
        return newRecipe;
    }

    public Recipe updateRecipe( String id, RecipeDto recipeDto) {
        Recipe oldRecipe = findRecipeById(id).orElseThrow(() -> new IdNotFoundException("Recipe with Id: " + id + " not found."));
        Recipe updatedRecipe = new Recipe(
                id,
                recipeDto.title(),
                oldRecipe.slug(),
                recipeDto.recipeIngredients()
        );
        recipeRepository.save(updatedRecipe);
        return updatedRecipe;
    }
}
