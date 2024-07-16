package com.github.kropsz.certificate_generator.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.kropsz.certificate_generator.service.UserCoursesService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("v1/certificate")
@RequiredArgsConstructor
public class CertificateController {
    
    private final UserCoursesService userCoursesService;

    @PostMapping("/generate/{userId}/{courseId}")
    public ResponseEntity<String> generateCertificate(@PathVariable String userId, @PathVariable String courseId) {
        userCoursesService.generateCertificate(userId, courseId);
        return ResponseEntity.ok("Certificate generated");
    }

}
