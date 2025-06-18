package org.bea.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.bea.backend.model.Ingredient;
import org.bea.backend.model.IngredientDto;
import org.bea.backend.model.Nutrients;
import org.bea.backend.openai.IngredientOpenAiDto;
import org.bea.backend.service.IngredientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

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

    @GetMapping("/name/{name}")
    public Set<Ingredient> getIngredientByName(@PathVariable String name) {
        return ingredientService.getIngredientByName(name);
    }
    @GetMapping("/ingredient/detail/{id}")
    public Ingredient getIngredientById(@PathVariable String id) {
        return ingredientService.getIngredientById(id);
    }

    @PostMapping
    public Ingredient addIngredient(@Valid @RequestBody IngredientDto ingredientDto){
        return ingredientService.addIngredient(ingredientDto);
    }
    @PutMapping("/{id}")
    public Ingredient updateIngredient(@PathVariable String id,  @Valid @RequestBody IngredientDto ingredientDto){
        return ingredientService.updateIngredient(id, ingredientDto);
    }
    @PostMapping("/openai/add")
    public Ingredient addIngredientByOpenAi(@Valid @RequestBody IngredientOpenAiDto ingredientOpenAiDto) throws JsonProcessingException {
        return ingredientService.addIngredientByOpenAi(ingredientOpenAiDto);
    }
    @GetMapping("/daily/nutrients")
    public Nutrients getNutrientsDaily() {
        return ingredientService.getNutrientsDaily();
    }
}
