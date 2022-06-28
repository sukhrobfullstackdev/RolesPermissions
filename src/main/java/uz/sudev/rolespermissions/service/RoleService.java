package uz.sudev.rolespermissions.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.sudev.rolespermissions.entity.Role;
import uz.sudev.rolespermissions.payload.Message;
import uz.sudev.rolespermissions.payload.RoleDto;
import uz.sudev.rolespermissions.repository.RoleRepository;

import java.util.Optional;

@Service
public class RoleService {
    final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public ResponseEntity<Message> addRole(RoleDto roleDto) {
        if (!roleRepository.existsByName(roleDto.getName())) {
            roleRepository.save(new Role(roleDto.getName(), roleDto.getPermissionIds(), roleDto.getDescription()));
            return ResponseEntity.status(HttpStatus.CREATED).body(new Message(true, "The role is successfully added!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "The role is already exists!"));
        }
    }

    public ResponseEntity<Message> editRole(Long id, RoleDto roleDto) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isPresent()) {
            Role role = optionalRole.get();
            role.setName(roleDto.getName());
            role.setDescription(roleDto.getDescription());
            role.setPermissions(roleDto.getPermissionIds());
            roleRepository.save(role);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Message(true, "The role is successfully edited!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The role is not found!"));
        }
    }

    public ResponseEntity<Message> deleteRole(Long id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isPresent()) {
            roleRepository.delete(optionalRole.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new Message(true, "The role is successfully deleted!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The role is not found!"));
        }
    }

    public ResponseEntity<Page<Role>> getRoles(int page, int size) {
        return ResponseEntity.ok(roleRepository.findAll(PageRequest.of(page, size)));
    }

    public ResponseEntity<Role> getRole(Long id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        return optionalRole.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }
}
