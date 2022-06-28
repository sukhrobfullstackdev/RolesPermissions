package uz.sudev.rolespermissions.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.sudev.rolespermissions.annotations.CheckPermission;
import uz.sudev.rolespermissions.entity.Post;
import uz.sudev.rolespermissions.payload.Message;
import uz.sudev.rolespermissions.payload.PostDto;
import uz.sudev.rolespermissions.service.PostService;

@RestController
@RequestMapping(value = "/post")
public class PostController {
    final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<Page<Post>> getPosts(@RequestParam int page, @RequestParam int size) {
        return postService.getPosts(page, size);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @CheckPermission(value = "ADD_POST")
    @PostMapping
    public ResponseEntity<Message> addPost(@RequestBody PostDto postDto) {
        return postService.addPost(postDto);
    }

    @CheckPermission(value = "EDIT_POST")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Message> editPost(@PathVariable Long id, @RequestBody PostDto postDto) {
        return postService.editPost(id, postDto);
    }
    @CheckPermission(value = "DELETE_POST")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Message> deletePost(@PathVariable Long id) {
        return postService.deletePost(id);
    }
}
