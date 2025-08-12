package com.dev.connect.Controller;

import com.dev.connect.apiResponse.PageableResponse;
import com.dev.connect.RequestDto.PostRequest;
import com.dev.connect.ResponseDto.PostResponse;
import com.dev.connect.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService postService;
    @PostMapping("/create")
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest postRequest,Principal principal){
            return new ResponseEntity<>(postService.createPost(postRequest,principal), HttpStatus.CREATED);
    }
    @GetMapping("/getAllPost")
    public ResponseEntity<PageableResponse<PostResponse>> getAllPost(
            @RequestParam(defaultValue = "0",required = false) int pageNumber,
            @RequestParam(defaultValue = "10",required = false) int pageSize,
            @RequestParam(defaultValue = "postId",required = false) String sortBY
    ){
            return new ResponseEntity<>(postService.gettAllPost(pageNumber,pageSize,sortBY),HttpStatus.OK);
    }

    @GetMapping("/getAllPostOfUser/{userId}")
    public ResponseEntity<PageableResponse<PostResponse>>getAllPostOfUser(
            @PathVariable String userId,
            @RequestParam(defaultValue = "0",required = false) int pageNumber,
            @RequestParam(defaultValue = "10",required = false) int pageSize,
            @RequestParam(defaultValue = "postId",required = false) String sortBY
    ){
            return new ResponseEntity<>(postService.getAllPostOfUser(userId,pageNumber,pageSize,sortBY),HttpStatus.OK);
    }

    @GetMapping("/getPostById/{postId}")
    public ResponseEntity<PostResponse>getPostById(@PathVariable(name = "postId") int postId){

          return new ResponseEntity<>(postService.getPostById(postId),HttpStatus.FOUND);
    }




    //admin only
    @PutMapping("/admin/edit/{postId}")
    public ResponseEntity<PostResponse> editPost(@PathVariable int postId ,@RequestBody PostRequest postRequest,Principal principal){
            return new ResponseEntity<>(postService.adminEditPost(postId,postRequest),HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/admin/delete/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable int postId, Principal principal){
            return new ResponseEntity<>(postService.adminDeletePost(postId),HttpStatus.OK);
    }
}
