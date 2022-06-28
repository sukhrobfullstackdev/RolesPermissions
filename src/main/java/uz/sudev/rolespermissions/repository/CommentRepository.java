package uz.sudev.rolespermissions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.sudev.rolespermissions.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
