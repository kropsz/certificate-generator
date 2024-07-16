package com.github.kropsz.certificate_generator.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.github.kropsz.certificate_generator.model.Course;

public interface CourseRepository extends MongoRepository<Course, String> {
    
}
