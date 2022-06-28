package uz.sudev.rolespermissions.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.sudev.rolespermissions.entity.Role;
import uz.sudev.rolespermissions.entity.User;
import uz.sudev.rolespermissions.payload.LoginDto;
import uz.sudev.rolespermissions.payload.Message;
import uz.sudev.rolespermissions.payload.RegisterDto;
import uz.sudev.rolespermissions.repository.AuthenticationRepository;
import uz.sudev.rolespermissions.repository.RoleRepository;
import uz.sudev.rolespermissions.security.JWTProvider;

import java.util.Optional;

@Service
public class AuthenticationService implements UserDetailsService {
    final AuthenticationRepository authenticationRepository;
    final RoleRepository roleRepository;
    final PasswordEncoder passwordEncoder;
    final AuthenticationManager authenticationManager;
    final JWTProvider jwtProvider;

    public AuthenticationService(JWTProvider jwtProvider, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, RoleRepository roleRepository, AuthenticationRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    public ResponseEntity<Message> register(RegisterDto registerDto) {
        if (registerDto.getPassword().equals(registerDto.getPrePassword())) {
            if (!authenticationRepository.existsByUsername(registerDto.getEmail())) {
                Optional<Role> optionalRole = roleRepository.findByName("USER");
                if (optionalRole.isPresent()) {
                    authenticationRepository.save(new User(registerDto.getFirstName(), registerDto.getLastName(), registerDto.getEmail(), passwordEncoder.encode(registerDto.getPassword()), optionalRole.get(), true));
                    return ResponseEntity.status(HttpStatus.CREATED).body(new Message(true, "You have successfully registered!"));
                } else {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(new Message(false, "The role which is given to user is not found!"));
                }
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new Message(false, "The email is already in use!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "Your password is not equals to pre-password!"));
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = authenticationRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new UsernameNotFoundException(username + " is not found!");
        }
    }

    public ResponseEntity<Message> login(LoginDto loginDto) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        User user = (User) authenticate.getPrincipal();
        String token = jwtProvider.generateToken(user.getUsername(), user.getRole());
        return ResponseEntity.status(HttpStatus.OK).body(new Message(true, "You have successfully logged!", token));
    }
}
