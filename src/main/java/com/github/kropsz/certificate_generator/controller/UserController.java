package com.github.kropsz.certificate_generator.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.kropsz.certificate_generator.dto.UserRequest;
import com.github.kropsz.certificate_generator.dto.UserResponse;
import com.github.kropsz.certificate_generator.registration.UserCourses;
import com.github.kropsz.certificate_generator.service.UserCoursesService;
import com.github.kropsz.certificate_generator.service.UserService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("v1/user")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    private final UserCoursesService userCoursesService;

    @PostMapping()
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRequest payload) {
        var entity = userService.registerUser(payload);
        return entity.map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String id) {
        var entity = userService.getUser(id);
        return entity.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{userId}/course/{courseId}")
    public ResponseEntity<UserCourses> registerUserToCourse(@PathVariable String userId, @PathVariable String courseId) {
        var register = userCoursesService.registerUserToCourse(userId, courseId);
        return ResponseEntity.ok(register);
    }
    
    @PutMapping("/{userId}/course/{courseId}/completed")
    public ResponseEntity<UserCourses> completeCourse(@PathVariable String userId, @PathVariable String courseId) {
        var register = userCoursesService.completeCourse(userId, courseId);
        return ResponseEntity.ok(register);
    }

}
