package org.bea.backend.controller;
import org.bea.backend.enums.UserTypes;
import org.bea.backend.exception.UserNotFoundException;
import org.bea.backend.model.User;
import org.bea.backend.model.UserDto;
import org.bea.backend.model.UserUpdateDto;
import org.bea.backend.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
/*** Anderer Login-Typ, z. B. Username/Password oder JWT
            return Map.of(
                "authType", authentication.getClass().getSimpleName(),
                "principal", authentication.getPrincipal()
            );
        }
***/
@RestController
@RequestMapping("eyf/users")
public class UserController {

    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) {
        return userService.getUserById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }
    
    @PostMapping("/insert")
    public User insertUser(@Valid @RequestBody UserDto userDto) {
        return userService.insertUser(userDto, UserTypes.EMAIL.name());
    }

    @PutMapping("/update/{id}")
    public User updateUser(@PathVariable String id, @Valid @RequestBody UserUpdateDto userDto) {
        return userService.updateUser(id, userDto);
    }
}
