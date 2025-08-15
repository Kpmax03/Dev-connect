package com.dev.connect.service;

import com.dev.connect.RequestDto.CommentRequest;
import com.dev.connect.RequestDto.MessageRequest;
import com.dev.connect.ResponseDto.MessageResponse;
import com.dev.connect.ResponseDto.ShortMessageResponse;
import com.dev.connect.entity.Message;

import java.security.Principal;
import java.util.List;

public interface MessageService {
    public List<ShortMessageResponse> getAllReceivedMessage(Principal principal);
    public List<ShortMessageResponse> getAllSendedMessage(Principal principal);
    public List<MessageResponse> viewMessageFromUser(String userId, Principal principal);
    public MessageResponse sendMessage(Principal principal, MessageRequest messageRequest, String reciever);
    public String deleteMessage(String messageId,Principal principal);
}
