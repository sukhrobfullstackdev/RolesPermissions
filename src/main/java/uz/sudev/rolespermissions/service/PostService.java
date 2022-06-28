package uz.sudev.rolespermissions.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.sudev.rolespermissions.entity.Post;
import uz.sudev.rolespermissions.payload.Message;
import uz.sudev.rolespermissions.payload.PostDto;
import uz.sudev.rolespermissions.repository.PostRepository;

import java.util.Optional;

@Service
public class PostService {
    final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public ResponseEntity<Page<Post>> getPosts(int page, int size) {
        return ResponseEntity.ok(postRepository.findAll(PageRequest.of(page, size)));
    }

    public ResponseEntity<Post> getPost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        return optionalPost.map(post -> ResponseEntity.status(HttpStatus.OK).body(post)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    public ResponseEntity<Message> addPost(PostDto postDto) {
        postRepository.save(new Post(postDto.getTitle(), postDto.getText(), postDto.getUrl()));
        return ResponseEntity.status(HttpStatus.CREATED).body(new Message(true, "The post is successfully added!"));
    }

    public ResponseEntity<Message> editPost(Long id, PostDto postDto) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.setText(postDto.getText());
            post.setTitle(postDto.getTitle());
            post.setUrl(postDto.getUrl());
            postRepository.save(post);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Message(true, "The post is successfully edited!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The post is not found!"));
        }
    }

    public ResponseEntity<Message> deletePost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            postRepository.delete(optionalPost.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new Message(true, "The post is successfully deleted!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The post is not found!"));
        }
    }
}
