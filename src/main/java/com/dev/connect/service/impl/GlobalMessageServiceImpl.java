package com.dev.connect.service.impl;

import com.dev.connect.RequestDto.GlobalMessageRequest;
import com.dev.connect.ResponseDto.GlobalMessageResponse;
import com.dev.connect.entity.GlobalMessage;
import com.dev.connect.repository.GlobalMessageRepository;
import com.dev.connect.repository.UserRepository;
import com.dev.connect.service.GlobalMessageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GlobalMessageServiceImpl implements GlobalMessageService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private GlobalMessageRepository globalMessageRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public GlobalMessageResponse sendMessageGlobaly(GlobalMessageRequest globalMessageRequest, Principal principal) {

        String sender = principal.getName();

        deleteAllMessageOlderThanOneWeek();

        GlobalMessage globalMessage = mapper.map(globalMessageRequest, GlobalMessage.class);

        globalMessage.setTimeStamp(LocalDateTime.now());
        globalMessage.setSender(sender);

        GlobalMessage save = globalMessageRepository.save(globalMessage);

        return mapper.map(save,GlobalMessageResponse.class);

    }

    @Override
    public List<GlobalMessageResponse> receiveAllMessageGlobal() {

        deleteAllMessageOlderThanOneWeek();

        List<GlobalMessageResponse> globalMessageResponseList = globalMessageRepository.findAllByOrderByTimeStampDesc().stream().map(oneMessage->mapper.map(oneMessage,GlobalMessageResponse.class)).collect(Collectors.toUnmodifiableList());
        return globalMessageResponseList;

    }
    private void deleteAllMessageOlderThanOneWeek(){

        List<GlobalMessage> all = globalMessageRepository.findAll();
        all.stream()
                .filter(oneMessage->oneMessage.getTimeStamp().isBefore(LocalDateTime.now().minusWeeks(1)))
                .forEach(expiredMessage->globalMessageRepository.delete(expiredMessage));
    }
}
