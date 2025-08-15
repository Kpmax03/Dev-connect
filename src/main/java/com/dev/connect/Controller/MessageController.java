package com.dev.connect.Controller;

import com.dev.connect.RequestDto.CommentRequest;
import com.dev.connect.ResponseDto.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/message")
public class MessageController {
    public ResponseEntity<MessageResponse> getAllReceivedMessage(Principal principal){

    }
    public ResponseEntity<MessageResponse> getAllSendedMessages(Principal principal){

    }

    @GetMapping("/getOneMessage/{messageId")
    public ResponseEntity<MessageResponse> viewMessageFromUser(@PathVariable String userId){

    }

    @PostMapping("/send/{receiverId}")
    public ResponseEntity<MessageResponse> sendMessage(@PathVariable String receiverId, @RequestBody CommentRequest commentRequest, Principal principal){

    }

    @DeleteMapping("/delete/{principal}")
    public ResponseEntity<String> deleteMessage(@PathVariable String messageId,Principal principal){

    }
}
