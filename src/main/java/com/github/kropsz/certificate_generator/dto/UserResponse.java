package com.github.kropsz.certificate_generator.dto;

import java.util.Set;

public record UserResponse(String id, String name, String lastName, String email, String birthDate, Set<String> coursesId) {
    
}
