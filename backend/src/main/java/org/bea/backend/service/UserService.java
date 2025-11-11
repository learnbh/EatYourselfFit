package org.bea.backend.service;

import java.time.Instant;
import java.util.Optional;

import org.bea.backend.enums.UserRoles;
import org.bea.backend.exception.IdNotFoundException;
import org.bea.backend.model.User;
import org.bea.backend.model.UserDto;
import org.bea.backend.repository.UserReprository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserReprository userReprository;
    private final ServiceId serviceId;

    public UserService(UserReprository userReprository, ServiceId serviceId) {
        this.userReprository = userReprository;
        this.serviceId = serviceId;
    }

    public Optional<User> getUserById(String id) {
         return userReprository.findById(id);
    }   

    public User insertUser(UserDto userDto, String id) { 
        Instant date = Instant.now();
        User newUser = new User(
            id,
            userDto.name(),
            userDto.email(),
            UserRoles.USER.name(),
            userDto.imageUrl(),
            userDto.type(),
            date,
            date);
        userReprository.save(newUser);
        return newUser;
    }

    public User updateUser(String id, UserDto userDto) {
        Optional<User> optionalUser = userReprository.findById(id);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            User updatedUser = new User(
                existingUser.id(),
                userDto.name() != null ? userDto.name() : existingUser.name(),
                userDto.email() != null ? userDto.email() : existingUser.email(),
                existingUser.role(),
                userDto.imageUrl() != null ? userDto.imageUrl() : existingUser.imageUrl(),
                existingUser.type(),
                existingUser.createdAt(),
                Instant.now()
            );
            userReprository.save(updatedUser);
            return updatedUser;
        }
        throw new IdNotFoundException("Id of User not found");
    }
}
