package com.dev.connect.Controller;

import com.dev.connect.ApiResponse.PageableResponse;
import com.dev.connect.dto.PostDto;
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
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
            return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }
    @PutMapping("/edit/{postId}")
    public ResponseEntity<PostDto> editPost(@PathVariable int postId ,@RequestBody PostDto postDto){
            return new ResponseEntity<>(postService.editPost(postId,postDto),HttpStatus.ACCEPTED);
    }
    @DeleteMapping("delete/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable int postId){
            return new ResponseEntity<>(postService.deletePost(postId),HttpStatus.OK);
    }
    @GetMapping("/getAllPost")
    public ResponseEntity<PageableResponse<PostDto>> getAllPost(
            @RequestParam(defaultValue = "0",required = false) int pageNumber,
            @RequestParam(defaultValue = "10",required = false) int pageSize,
            @RequestParam(defaultValue = "postId",required = false) String sortBY
    ){
            return new ResponseEntity<>(postService.gettAllPost(pageNumber,pageSize,sortBY),HttpStatus.OK);
    }
    @GetMapping("/getAllPostOfUser/{userId}")
    public ResponseEntity<PageableResponse<PostDto>>getAllPostOfUser(
            @PathVariable String userId,
            @RequestParam(defaultValue = "0",required = false) int pageNumber,
            @RequestParam(defaultValue = "10",required = false) int pageSize,
            @RequestParam(defaultValue = "postId",required = false) String sortBY
    ){
            return new ResponseEntity<>(postService.getAllPostOfUser(userId,pageNumber,pageSize,sortBY),HttpStatus.OK);
    }
    @GetMapping("/getPostById/{postId}")
    public ResponseEntity<PostDto>getPostById(@PathVariable(name = "postId") int postId){

          return new ResponseEntity<>(postService.getPostById(postId),HttpStatus.FOUND);
    }
}
