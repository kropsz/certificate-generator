package com.github.kropsz.certificate_generator.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.kropsz.certificate_generator.dto.CourseRequest;
import com.github.kropsz.certificate_generator.dto.CourseResponse;
import com.github.kropsz.certificate_generator.service.CourseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("v1/course")
@RequiredArgsConstructor
public class CouseController {
 
    private final CourseService courseService;


    @PostMapping()
    public ResponseEntity<CourseResponse> registerCourse(@RequestBody CourseRequest payload) {
        var entity = courseService.registerCourse(payload);
        return entity.map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping()
    public ResponseEntity<List<CourseResponse>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }
}
