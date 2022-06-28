package uz.sudev.rolespermissions.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.sudev.rolespermissions.annotations.CheckPermission;
import uz.sudev.rolespermissions.entity.User;
import uz.sudev.rolespermissions.payload.Message;
import uz.sudev.rolespermissions.payload.UserDto;
import uz.sudev.rolespermissions.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @CheckPermission(value = "VIEW_USERS")
    @GetMapping(value = "/get")
    public ResponseEntity<Page<User>> getUsers(@RequestParam int page, @RequestParam int size) {
        return userService.getUsers(page,size);
    }

    @CheckPermission(value = "VIEW_USERS")
    @GetMapping(value = "/get/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @CheckPermission(value = "ADD_USER")
    @PostMapping(value = "/add")
    public ResponseEntity<Message> addUser(@Valid @RequestBody UserDto userDto) {
        return userService.addUser(userDto);
    }

    @CheckPermission(value = "EDIT_USER")
    @PutMapping(value = "/edit/{id}")
    public ResponseEntity<Message> editUser(@PathVariable Long id, @Valid @RequestBody UserDto userDto) {
        return userService.editUser(id, userDto);
    }

    @CheckPermission(value = "DELETE_USER")
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Message> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
}
