package uz.sudev.rolespermissions.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.sudev.rolespermissions.annotations.CheckPermission;
import uz.sudev.rolespermissions.entity.Comment;
import uz.sudev.rolespermissions.payload.CommentDto;
import uz.sudev.rolespermissions.payload.Message;
import uz.sudev.rolespermissions.service.CommentService;

@RestController
@RequestMapping(value = "/comment")
public class CommentController {
    final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<Page<Comment>> getComments(@RequestParam int page, @RequestParam int size) {
        return commentService.getComments(page, size);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Comment> getComment(@PathVariable Long id) {
        return commentService.getComment(id);
    }

    @CheckPermission(value = "ADD_COMMENT")
    @PostMapping
    public ResponseEntity<Message> addComment(@RequestBody CommentDto commentDto) {
        return commentService.addComment(commentDto);
    }

    @CheckPermission(value = "EDIT_COMMENT")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Message> editComment(@PathVariable Long id, @RequestBody CommentDto commentDto) {
        return commentService.editComment(id, commentDto);
    }

    @CheckPermission(value = "DELETE_COMMENT")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Message> deleteComment(@PathVariable Long id) {
        return commentService.deleteComment(id);
    }
    @CheckPermission(value = "DELETE_MY_COMMENT")
    @DeleteMapping(value = "/own/{id}")
    public ResponseEntity<Message> deleteMyComment(@PathVariable Long id) {
        return commentService.deleteMyComment(id);
    }
}
