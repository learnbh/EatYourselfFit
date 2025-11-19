package org.bea.backend.repository;

import org.bea.backend.model.User;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@Document(collation = "Users")
public interface UserReprository extends MongoRepository<User, String>{
}
