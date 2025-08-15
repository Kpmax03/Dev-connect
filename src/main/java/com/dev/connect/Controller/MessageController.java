package com.dev.connect.Controller;

import com.dev.connect.RequestDto.CommentRequest;
import com.dev.connect.RequestDto.MessageRequest;
import com.dev.connect.ResponseDto.MessageResponse;
import com.dev.connect.ResponseDto.ShortMessageResponse;
import com.dev.connect.entity.Message;
import com.dev.connect.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/send/{receiverId}")
    public ResponseEntity<MessageResponse> sendMessage(@PathVariable String receiverId, @RequestBody MessageRequest messageRequest, Principal principal){
         return new ResponseEntity<>(messageService.sendMessage(principal,messageRequest,receiverId), HttpStatus.OK);
    }

    @GetMapping("/receive")
    public ResponseEntity<List<ShortMessageResponse>> getAllReceivedMessage(Principal principal){
        return new ResponseEntity<>(messageService.getAllReceivedMessage(principal),HttpStatus.OK);
    }

    @GetMapping("/send")
    public ResponseEntity<List<ShortMessageResponse>> getAllSendedMessages(Principal principal){
        return new ResponseEntity<>(messageService.getAllSendedMessage(principal),HttpStatus.OK);
    }

    @GetMapping("/viewPersonaly/{userId}")
    public ResponseEntity<List<MessageResponse>> viewMessageFromUser(@PathVariable String userId, Principal principal){
      return new ResponseEntity<>(messageService.viewMessageFromUser(userId,principal),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{messageId}")
    public ResponseEntity<String> deleteMessage(@PathVariable String messageId,Principal principal){
        return new ResponseEntity<>(messageService.deleteMessage(messageId,principal),HttpStatus.ACCEPTED);
    }

}
