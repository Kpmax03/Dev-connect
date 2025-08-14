package com.dev.connect.Controller;

import com.dev.connect.RequestDto.CommentRequest;
import com.dev.connect.ResponseDto.CommentResponse;
import com.dev.connect.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/create/{postId}")
    public ResponseEntity<CommentResponse> createComment(@PathVariable int postId, @RequestBody CommentRequest commentRequest, Principal principal){

        return new ResponseEntity<>(commentService.createComment(postId,commentRequest,principal), HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable String commentId,Principal principal){
        return new ResponseEntity<>(commentService.deleteComment(commentId,principal),HttpStatus.ACCEPTED);
    }
    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentResponse>> seeCommentsOfSpecificPost(@PathVariable int postId){
        return new ResponseEntity<>(commentService.seeCommentOfSpecificPost(postId),HttpStatus.OK);
    }

    //admin only
    @DeleteMapping("/admin/delete/{commentId}")
    public ResponseEntity<String> adminDeleteComment(@PathVariable String commentId){
       return new ResponseEntity<>(commentService.adminDeleteComment(commentId),HttpStatus.ACCEPTED);
    }
}
