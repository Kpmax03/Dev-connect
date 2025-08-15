package com.dev.connect.service.Impl;

import com.dev.connect.RequestDto.CommentRequest;
import com.dev.connect.RequestDto.MessageRequest;
import com.dev.connect.ResponseDto.MessageResponse;
import com.dev.connect.ResponseDto.ShortMessageResponse;
import com.dev.connect.config.CustomMethods;
import com.dev.connect.entity.Message;
import com.dev.connect.entity.User;
import com.dev.connect.exception.InvalidCradentialException;
import com.dev.connect.exception.ResourceNotFoundException;
import com.dev.connect.repository.MessageRepository;
import com.dev.connect.repository.UserRepository;
import com.dev.connect.service.MessageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public MessageResponse sendMessage(Principal principal, MessageRequest messageRequest, String recieverId) {
        String principalName = principal.getName();
        User sender = userRepository.findByEmail(principalName).orElseThrow(() -> new ResourceNotFoundException());

        User receiver = userRepository.findById(recieverId).orElseThrow(() -> new ResourceNotFoundException("receiver not found"));

        Message message = Message.builder()
                .messageId(UUID.randomUUID().toString())
                .messagedAt(LocalDateTime.now())
                .sender(sender)
                .receiver(receiver)
                .title(messageRequest.getTitle())
                .content(messageRequest.getContent())
                .build();

        Message save = messageRepository.save(message);

        MessageResponse messageResponse = CustomMethods.getMessageResponse(save);

        return messageResponse;
    }

    @Override
    public List<ShortMessageResponse> getAllReceivedMessage(Principal principal) {

        String principalName = principal.getName();
        User user = userRepository.findByEmail(principalName).orElseThrow(() -> new ResourceNotFoundException());

        List<Message> receivedMessageList = messageRepository.findByReceiverOrderByMessagedAtDesc(user);

        List<ShortMessageResponse> shortMessageResponses = receivedMessageList.stream().map(oneMessage -> {
            return ShortMessageResponse.builder()
                    .from(oneMessage.getSender().getEmail())
                    .to(oneMessage.getReceiver().getEmail())
                    .title(oneMessage.getTitle())
                    .time(oneMessage.getMessagedAt())
                    .build();
        }).collect(Collectors.toUnmodifiableList());

        return shortMessageResponses;

    }

    @Override
    public List<ShortMessageResponse> getAllSendedMessage(Principal principal) {

        String principalName = principal.getName();
        User user = userRepository.findByEmail(principalName).orElseThrow(() -> new ResourceNotFoundException());

        List<Message> bySenderOrderByMessagedAtDesc = messageRepository.findBySenderOrderByMessagedAtDesc(user);

        List<ShortMessageResponse> shortMessageResponsesList = bySenderOrderByMessagedAtDesc.stream().map(oneMessage -> {
            return ShortMessageResponse.builder()
                    .from(oneMessage.getSender().getEmail())
                    .to(oneMessage.getReceiver().getEmail())
                    .title(oneMessage.getTitle())
                    .time(oneMessage.getMessagedAt())
                    .build();
        }).collect(Collectors.toUnmodifiableList());

        return shortMessageResponsesList;
    }


    @Override
    public List<MessageResponse> viewMessageFromUser(String userId,Principal principal) {

        User selfUser = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new ResourceNotFoundException());
        User user2 = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found "));

        List<Message> byTwoUserChat = messageRepository.findByTwoUserChat(selfUser, user2);
        List<MessageResponse> messageResponseList = byTwoUserChat.stream().map(oneMessage -> {
            return CustomMethods.getMessageResponse(oneMessage);
        }).collect(Collectors.toUnmodifiableList());

        return messageResponseList;
    }


    @Override
    public String deleteMessage(String messageId, Principal principal) {

        String principalName = principal.getName();
        User user = userRepository.findByEmail(principalName).orElseThrow(() -> new ResourceNotFoundException());
        String email = user.getEmail();

        Message message = messageRepository.findById(messageId).orElseThrow(() -> new ResourceNotFoundException("conversation not found"));

        if(email.equals(message.getSender().getEmail()) || email.equals(message.getReceiver().getEmail())){
            messageRepository.delete(message);
            return "deleted successfully message between "+message.getSender().getEmail()+" to "+message.getReceiver().getEmail();
        }else
            throw new InvalidCradentialException("this comment not owns you");

    }
}
