package org.bea.backend.service;

import java.time.Instant;
import java.util.Optional;

import org.bea.backend.enums.UserRoles;
import org.bea.backend.enums.UserTypes;
import org.bea.backend.exception.IdNotFoundException;
import org.bea.backend.model.User;
import org.bea.backend.model.UserDto;
import org.bea.backend.model.UserUpdateDto;
import org.bea.backend.repository.UserReprository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final ServiceId serviceId;

    private final UserReprository userReprository;
    private final DateService dateService;

    public UserService(UserReprository userReprository, DateService dateService, ServiceId serviceId) {
        this.userReprository = userReprository;
        this.dateService = dateService;
        this.serviceId = serviceId;
    }

    public Optional<User> getUserById(String id) {
         return userReprository.findById(id);
    }   

    public User insertUser(UserDto userDto, String id) { 
        Instant date = dateService.getCurrentInstant();        
        if(id.equals(UserTypes.EMAIL.name())){
            id = "email_" + serviceId.generateId();
        }
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

    public User updateUser(String id, UserUpdateDto userDto) {
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
                dateService.getCurrentInstant()
            );
            userReprository.save(updatedUser);
            return updatedUser;
        }
        throw new IdNotFoundException("Id " + id + " of User not found");
    }
}
