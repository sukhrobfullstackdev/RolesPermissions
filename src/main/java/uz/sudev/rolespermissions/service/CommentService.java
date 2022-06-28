package uz.sudev.rolespermissions.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.sudev.rolespermissions.entity.Comment;
import uz.sudev.rolespermissions.entity.Post;
import uz.sudev.rolespermissions.entity.User;
import uz.sudev.rolespermissions.payload.CommentDto;
import uz.sudev.rolespermissions.payload.Message;
import uz.sudev.rolespermissions.repository.CommentRepository;
import uz.sudev.rolespermissions.repository.PostRepository;

import java.util.Optional;

@Service
public class CommentService {
    final CommentRepository commentRepository;
    final PostRepository postRepository;

    public CommentService(PostRepository postRepository, CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public ResponseEntity<Page<Comment>> getComments(int page, int size) {
        return ResponseEntity.ok(commentRepository.findAll(PageRequest.of(page, size)));
    }

    public ResponseEntity<Comment> getComment(Long id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        return optionalComment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    public ResponseEntity<Message> addComment(CommentDto commentDto) {
        Optional<Post> optionalPost = postRepository.findById(commentDto.getPostId());
        if (optionalPost.isPresent()) {
            commentRepository.save(new Comment(commentDto.getText(), optionalPost.get()));
            return ResponseEntity.status(HttpStatus.CREATED).body(new Message(true, "The comment is successfully added!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The post is not found!"));
        }
    }

    public ResponseEntity<Message> editComment(Long id, CommentDto commentDto) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isPresent()) {
            Optional<Post> optionalPost = postRepository.findById(commentDto.getPostId());
            if (optionalPost.isPresent()) {
                Comment comment = optionalComment.get();
                comment.setText(commentDto.getText());
                comment.setPost(optionalPost.get());
                commentRepository.save(comment);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Message(true, "The comment is successfully edited!"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The post is not found!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The comment is not found!"));
        }
    }

    public ResponseEntity<Message> deleteComment(Long id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isPresent()) {
            commentRepository.delete(optionalComment.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new Message(true, "The comment is successfully deleted!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The comment is not found!"));
        }
    }

    public ResponseEntity<Message> deleteMyComment(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Optional<Comment> optionalComment = commentRepository.findById(id);
            if (optionalComment.isPresent()) {
                User user = (User) authentication.getPrincipal();
                Comment comment = optionalComment.get();
                User createdBy = comment.getCreatedBy();
                if (createdBy.equals(user)) {
                    commentRepository.delete(comment);
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new Message(true, "Your comment is successfully deleted!"));
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "You can delete only your comments!"));
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The comment is not found!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Message(false, "Please log in!"));
        }
    }
}
