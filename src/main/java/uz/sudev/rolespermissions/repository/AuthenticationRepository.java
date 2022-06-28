package uz.sudev.rolespermissions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.sudev.rolespermissions.entity.User;

import java.util.Optional;

@Repository
public interface AuthenticationRepository extends JpaRepository<User,Long> {
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
}
