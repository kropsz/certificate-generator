package com.github.kropsz.certificate_generator.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.github.kropsz.certificate_generator.dto.CourseRequest;
import com.github.kropsz.certificate_generator.dto.CourseResponse;
import com.github.kropsz.certificate_generator.model.Course;
import com.github.kropsz.certificate_generator.repository.CourseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public Optional<CourseResponse> registerCourse(CourseRequest payload) {
        var course = new Course(payload.name(),
                payload.description(),
                payload.duration(),
                payload.teacher());
        courseRepository.save(course);
        return Optional.of(toResponse(course));
    }

    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public CourseResponse toResponse(Course course) {
        return new CourseResponse(course.getId(),
                course.getName(),
                course.getDescription(),
                course.getDuration(),
                course.getTeacher());
    }

}
