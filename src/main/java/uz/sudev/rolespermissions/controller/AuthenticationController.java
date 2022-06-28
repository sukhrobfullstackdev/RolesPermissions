package uz.sudev.rolespermissions.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.sudev.rolespermissions.payload.LoginDto;
import uz.sudev.rolespermissions.payload.Message;
import uz.sudev.rolespermissions.payload.RegisterDto;
import uz.sudev.rolespermissions.service.AuthenticationService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/authentication")
public class AuthenticationController {
    final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Message> register(@Valid @RequestBody RegisterDto registerDto) {
        return authenticationService.register(registerDto);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Message> login(@Valid @RequestBody LoginDto loginDto) {
        return authenticationService.login(loginDto);
    }
}
