package org.bea.backend.repository;

import org.bea.backend.model.Nutrients;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@Document(collation = "Nutrients")
public interface NutrientsRepository extends MongoRepository<Nutrients, String> {
}
