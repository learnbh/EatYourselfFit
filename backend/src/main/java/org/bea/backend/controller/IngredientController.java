package org.bea.backend.controller;

import org.bea.backend.model.Ingredient;
import org.bea.backend.model.IngredientDto;
import org.bea.backend.service.IngredientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eyf/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping
    public List<Ingredient> getIngredients() {
        return ingredientService.getIngredients();
    }

    @PostMapping
    public Ingredient addIngredient(@RequestBody IngredientDto ingredientDto) {
        return ingredientService.addIngredient(ingredientDto);
    }
}
