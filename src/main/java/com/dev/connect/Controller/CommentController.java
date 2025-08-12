package com.dev.connect.Controller;

import com.dev.connect.RequestDto.CommentRequest;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/create")
public class CommentController {
    @PostMapping()
    public String createComment(@PathVariable int postId, @RequestBody CommentRequest commentRequest, Principal principal){
        return null;
    }
    @DeleteMapping("/delete/{commentId}")
    public String deleteComment(@PathVariable String commentId,Principal principal){
        return null;
    }
    @GetMapping("/{postId}")
    public void seeCommentsOfSpecificPost(@PathVariable int postId,Principal principal){

    }

    //admin only
    @DeleteMapping("/admin/delete/{commentId}")
    public void adminDeleteComment(@PathVariable String commentId){

    }
}
