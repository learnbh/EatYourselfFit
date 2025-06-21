package org.bea.backend.repository;

import org.bea.backend.model.Ingredient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientRepository extends MongoRepository<Ingredient, String> {
    Optional<Ingredient> getIngredientByProductAndVariationContainsIgnoreCase(String product, String variation);
    List<Ingredient> findIngredientsByProductContainsIgnoreCase(String product);
    List<Ingredient> findIngredientsByVariationContainsIgnoreCase(String variation);
}
