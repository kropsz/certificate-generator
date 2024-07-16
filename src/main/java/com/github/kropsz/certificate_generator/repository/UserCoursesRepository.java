package com.github.kropsz.certificate_generator.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.github.kropsz.certificate_generator.registration.UserCourses;

public interface UserCoursesRepository extends MongoRepository<UserCourses, String> {

    Optional<UserCourses> findByUserIdAndCourseId(String userId, String courseId);
    
}
