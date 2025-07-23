package org.bea.backend.migrate.service;

import lombok.extern.slf4j.Slf4j;
import org.bea.backend.model.Ingredient;
import org.bea.backend.model.Recipe;
import org.bea.backend.repository.IngredientRepository;
import org.bea.backend.repository.RecipeRepository;
import org.bea.backend.service.ServiceId;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class MigrateSlugService {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final ServiceId serviceId;

    public MigrateSlugService(
            RecipeRepository recipeRepository,
            IngredientRepository ingredientRepository,
            ServiceId serviceId
    ){
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.serviceId = serviceId;
    }

    public boolean migrateRecipeAddSlug() {
        System.out.println("migrateRecipeAddSlug");
        List<Recipe> recipes = recipeRepository.findAll();
        for (Recipe recipe : recipes) {
            String title = recipe.title();
            String slug = serviceId.generateSlug(title);
            Recipe recipeWithSlug = new Recipe(
                    recipe.id(),
                    title,
                    slug,
                    recipe.recipeIngredients()
            );
            System.out.println(recipeWithSlug);
            recipeRepository.save(recipeWithSlug);
        }
        return true;
    }
    public boolean migrateIngredientAddSlug() {
        List<Ingredient> ingredients = ingredientRepository.findAll();
        for (Ingredient ingredient : ingredients) {
            try {
                if (ingredient.slug() == null) {
                    String variation = "";
                    if (ingredient.variation() != null) {
                        variation = ingredient.variation();
                    }
                    String product = ingredient.product();
                    String slug = serviceId.generateSlug(product + "-" + variation);
                    Ingredient ingredientWithSlug = new Ingredient(
                            ingredient.id(),
                            product,
                            variation,
                            slug,
                            ingredient.quantity(),
                            ingredient.unit(),
                            ingredient.prices(),
                            ingredient.nutrientsId()
                    );
                    ingredientRepository.save(ingredientWithSlug);
                }
            } catch (Exception e) {
                log.error("Migration failed on ingredient with id  {}: {}", ingredient.id(), e.getMessage(), e);
            }
        }
        return true;
    }
}
