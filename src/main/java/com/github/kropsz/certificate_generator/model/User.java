package com.github.kropsz.certificate_generator.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "users")
@Data @AllArgsConstructor @NoArgsConstructor
public class User {
    
    @Id
    private String id;
    private String name;
    private String lastName;
    @Indexed(unique = true)
    private String email;
    private LocalDate birthDate;
    private Set<String> coursesId = new HashSet<>();     


    public User(String name, String lastName, String email, LocalDate birthDate) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.birthDate = birthDate;
    }

    public void addCourse(String courseId) {
        coursesId.add(courseId);
    }

    public void removeCourse(String courseId) {
        coursesId.remove(courseId);
    }
}
