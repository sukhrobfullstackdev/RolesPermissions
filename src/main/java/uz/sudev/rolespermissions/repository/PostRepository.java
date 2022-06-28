package uz.sudev.rolespermissions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.sudev.rolespermissions.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
}
