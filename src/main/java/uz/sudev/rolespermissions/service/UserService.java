package uz.sudev.rolespermissions.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.sudev.rolespermissions.entity.Role;
import uz.sudev.rolespermissions.entity.User;
import uz.sudev.rolespermissions.payload.Message;
import uz.sudev.rolespermissions.payload.UserDto;
import uz.sudev.rolespermissions.repository.AuthenticationRepository;
import uz.sudev.rolespermissions.repository.RoleRepository;

import java.util.Optional;

@Service
public class UserService {
    final AuthenticationRepository authenticationRepository;
    final RoleRepository roleRepository;
    final PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder, RoleRepository roleRepository, AuthenticationRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<Message> addUser(UserDto userDto) {
        if (!authenticationRepository.existsByUsername(userDto.getEmail())) {
            Optional<Role> optionalRole = roleRepository.findById(userDto.getRoleId());
            if (optionalRole.isPresent()) {
                authenticationRepository.save(new User(userDto.getFirstName(), userDto.getLastName(), userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()), optionalRole.get(), true));
                return ResponseEntity.status(HttpStatus.CREATED).body(new Message(true, "The user is successfully saved!"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The selected role is not found!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "The email is already in use!"));
        }
    }

    public ResponseEntity<Message> editUser(Long id, UserDto userDto) {
        Optional<User> optionalUser = authenticationRepository.findById(id);
        if (optionalUser.isPresent()) {
            Optional<Role> optionalRole = roleRepository.findById(userDto.getRoleId());
            if (optionalRole.isPresent()) {
                User user = optionalUser.get();
                user.setFirstName(userDto.getFirstName());
                user.setLastName(userDto.getLastName());
                user.setUsername(userDto.getEmail());
                user.setPassword(passwordEncoder.encode(userDto.getPassword()));
                user.setRole(optionalRole.get());
                authenticationRepository.save(user);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Message(true, "The user is successfully edited!"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The selected role is not found!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The user is not found!"));
        }
    }

    public ResponseEntity<Message> deleteUser(Long id) {
        Optional<User> optionalUser = authenticationRepository.findById(id);
        if (optionalUser.isPresent()) {
            authenticationRepository.delete(optionalUser.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new Message(true, "The user is successfully deleted!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The user is not found!"));
        }
    }

    public ResponseEntity<Page<User>> getUsers(int page, int size) {
        return ResponseEntity.ok(authenticationRepository.findAll(PageRequest.of(page, size)));
    }

    public ResponseEntity<User> getUser(Long id) {
        Optional<User> optionalUser = authenticationRepository.findById(id);
        return optionalUser.map(user -> ResponseEntity.status(HttpStatus.OK).body(user)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }
}
