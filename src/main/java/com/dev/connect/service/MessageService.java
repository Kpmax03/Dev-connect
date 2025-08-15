package com.dev.connect.service;

import com.dev.connect.RequestDto.CommentRequest;
import com.dev.connect.ResponseDto.MessageResponse;

import java.security.Principal;

public interface MessageService {
    public void getAllReceivedMessage(Principal principal);
    public void getAllSendedMessage(Principal principal);
    public void viewMessageFromUser(String userId);
    public MessageResponse sendMessage(Principal principal, CommentRequest commentRequest,String reciever);
    public String deleteMessage(String messageId,Principal principal);
}
