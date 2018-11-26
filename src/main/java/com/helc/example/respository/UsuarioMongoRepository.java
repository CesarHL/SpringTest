package com.helc.example.respository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.helc.example.model.User;

@Repository
public interface UsuarioMongoRepository extends MongoRepository<User, Long> {

}
