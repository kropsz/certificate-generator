package com.github.kropsz.certificate_generator.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.github.kropsz.certificate_generator.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    
    
}
