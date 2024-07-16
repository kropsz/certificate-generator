package com.github.kropsz.certificate_generator.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.github.kropsz.certificate_generator.dto.UserRequest;
import com.github.kropsz.certificate_generator.dto.UserResponse;
import com.github.kropsz.certificate_generator.model.User;
import com.github.kropsz.certificate_generator.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<UserResponse> registerUser(UserRequest payload) {
        var user = new User(payload.name(),
                payload.lastName(),
                payload.email(),
                LocalDate.parse(payload.birthDate()));

        userRepository.save(user);

        return Optional.of(buildUserResponse(user));
    }

    public Optional<UserResponse> getUser(String id) {
        return userRepository.findById(id).map(this::buildUserResponse);
    }

    public UserResponse buildUserResponse(User user) {
        return new UserResponse(user.getId(),
                user.getName(),
                user.getLastName(),
                user.getEmail(),
                String.valueOf(user.getBirthDate()),
                user.getCoursesId());
    }

}
