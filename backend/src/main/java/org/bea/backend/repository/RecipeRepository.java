package org.bea.backend.repository;

import org.bea.backend.model.Recipe;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@Document(collation = "Recipe")
public interface RecipeRepository extends MongoRepository<Recipe, String> {
}
