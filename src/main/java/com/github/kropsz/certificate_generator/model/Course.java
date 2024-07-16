package com.github.kropsz.certificate_generator.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "courses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    public Course(String name, String description, int duration, String teacher) {
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.teacher = teacher;
    }

    @Id
    private String id;
    private String name;
    private String description;
    private int duration;
    private String teacher;

}
