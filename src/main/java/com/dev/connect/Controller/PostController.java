package com.dev.connect.Controller;

import com.dev.connect.apiResponse.PageableResponse;
import com.dev.connect.RequestDto.PostRequest;
import com.dev.connect.ResponseDto.PostResponse;
import com.dev.connect.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/post")
@Tag(name = "post anything here")
public class PostController {

    @Autowired
    private PostService postService;

    @Operation(summary = "add post ")
    @PostMapping("/create")
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest postRequest,Principal principal){
            return new ResponseEntity<>(postService.createPost(postRequest,principal), HttpStatus.CREATED);
    }

    @Operation(summary = "edit post ")
    @PutMapping("/edit/{postId}")
    public ResponseEntity<PostResponse> editPost(@PathVariable int postId ,@RequestBody PostRequest postRequest,Principal principal){
        return new ResponseEntity<>(postService.editPost(postId,postRequest,principal),HttpStatus.ACCEPTED);
    }

    @Operation(summary = "delete post")
    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable int postId, Principal principal){
        return new ResponseEntity<>(postService.deletePost(postId,principal),HttpStatus.OK);
    }

    @Operation(summary = "get all post")
    @GetMapping("/getAllPost")
    public ResponseEntity<PageableResponse<PostResponse>> getAllPost(
            @RequestParam(defaultValue = "0",required = false) int pageNumber,
            @RequestParam(defaultValue = "10",required = false) int pageSize,
            @RequestParam(defaultValue = "postId",required = false) String sortBY
    ){
            return new ResponseEntity<>(postService.gettAllPost(pageNumber,pageSize,sortBY),HttpStatus.OK);
    }

    @Operation(summary = "get all post of specific user")
    @GetMapping("/getAllPostOfUser/{userId}")
    public ResponseEntity<PageableResponse<PostResponse>>getAllPostOfUser(
            @PathVariable String userId,
            @RequestParam(defaultValue = "0",required = false) int pageNumber,
            @RequestParam(defaultValue = "10",required = false) int pageSize,
            @RequestParam(defaultValue = "postId",required = false) String sortBY
    ){
            return new ResponseEntity<>(postService.getAllPostOfUser(userId,pageNumber,pageSize,sortBY),HttpStatus.OK);
    }

    @Operation(summary = "get post by their id ")
    @GetMapping("/getPostById/{postId}")
    public ResponseEntity<PostResponse>getPostById(@PathVariable(name = "postId") int postId){
          return new ResponseEntity<>(postService.getPostById(postId),HttpStatus.FOUND);
    }

    //search for specific tags
//    @GetMapping("/search")
//    public ResponseEntity<List<PostResponse>> searchPostBySpecificTags(@RequestParam List<String> tags){
//        return ResponseEntity.ok(postService.searchPostBySpecificTags(tags));
//    }
//
//    @GetMapping("/search")
//    public ResponseEntity<List<PostResponse>> searchPostByType(@RequestParam String type){
//       return ResponseEntity.ok(postService.searchPostByType(type));
//    }

    @GetMapping("/search")
    public ResponseEntity<List<PostResponse>> searchPostByTypeAndTags(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) List<String> tags
    ){
// case 1 : if user only want post type not tags
        if((type!=null && !type.isEmpty()) && (tags==null || tags.isEmpty())){

        return ResponseEntity.ok(postService.searchPostByType(type));
//case 2 : if user only wants post realted to tags not type
        } else if ((type==null || type.isEmpty()) && (tags!=null && !tags.isEmpty())) {

       return ResponseEntity.ok(postService.searchPostBySpecificTags(tags));
// case 3 : if user wants type of post that contains specific tags
        }else {
            return ResponseEntity.ok(postService.searchPostByTypeAndTags(type, tags));
        }
    }

    //admin only
    @PutMapping("/admin/edit/{postId}")
    public ResponseEntity<PostResponse> adminEditPost(@PathVariable int postId ,@RequestBody PostRequest postRequest,Principal principal){
            return new ResponseEntity<>(postService.adminEditPost(postId,postRequest),HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/admin/delete/{postId}")
    public ResponseEntity<String> adminDeletePost(@PathVariable int postId, Principal principal){
            return new ResponseEntity<>(postService.adminDeletePost(postId),HttpStatus.OK);
    }
}
