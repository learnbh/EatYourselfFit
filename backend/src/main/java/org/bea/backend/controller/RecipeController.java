package org.bea.backend.controller;

import org.bea.backend.model.Recipe;
import org.bea.backend.model.RecipeDto;
import org.bea.backend.service.RecipeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eyf/recipe")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }
    @GetMapping
    public List<Recipe> getAllRecipes() {
        return recipeService.getAllRecipes();
    }
    @GetMapping("/{id}")
    public Recipe getRecipeById(@PathVariable String id) {
        return recipeService.getRecipeById(id);
    }
    @PostMapping
    public Recipe addRecipe(@RequestBody RecipeDto recipeDto) {
        return recipeService.addRecipe(recipeDto);
    }
    @PutMapping("/{id}")
    public Recipe updateRecipe(@PathVariable String id, @RequestBody RecipeDto recipeDto) {
        return recipeService.updateRecipe(id, recipeDto);
    }
}
