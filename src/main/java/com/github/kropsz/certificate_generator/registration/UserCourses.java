package com.github.kropsz.certificate_generator.registration;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "userCourses")
@Data @AllArgsConstructor @NoArgsConstructor
@Builder
public class UserCourses {
    
    @Id
    private String id;
    private String userId;
    private String courseId;
    private String certificateId;
    private boolean completed;
    private LocalDate startDate;
    private LocalDate endDate;
    
}
