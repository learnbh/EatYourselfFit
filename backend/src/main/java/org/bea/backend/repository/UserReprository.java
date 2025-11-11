package org.bea.backend.repository;

import org.bea.backend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReprository extends MongoRepository<User, String>{
}
