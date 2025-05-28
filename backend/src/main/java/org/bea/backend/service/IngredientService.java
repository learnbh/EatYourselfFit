package org.bea.backend.service;

import org.bea.backend.model.Ingredient;
import org.bea.backend.model.IngredientDto;
import org.bea.backend.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final ServiceId serviceId;

    public IngredientService(IngredientRepository ingredientRepository, ServiceId serviceId) {
        this.ingredientRepository = ingredientRepository;
        this.serviceId = serviceId;
    }

    public List<Ingredient> getIngredients() {
        return ingredientRepository.findAll();
    }

    public Ingredient addIngredient(IngredientDto ingredientDto) {
        Ingredient newIngredient = new Ingredient(
                serviceId.generateId(),
                ingredientDto.product(),
                ingredientDto.variation(),
                ingredientDto.quantity(),
                ingredientDto.unit(),
                ingredientDto.prices(),
                ingredientDto.nutrientsId()
        );

        ingredientRepository.save(newIngredient);
        return newIngredient;
    }
}
