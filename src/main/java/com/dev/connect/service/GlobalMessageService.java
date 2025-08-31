package com.dev.connect.service;

import com.dev.connect.RequestDto.GlobalMessageRequest;
import com.dev.connect.ResponseDto.GlobalMessageResponse;

import java.security.Principal;
import java.util.List;

public interface GlobalMessageService {

    public GlobalMessageResponse sendMessageGlobaly(GlobalMessageRequest globalMessageRequest, Principal principal);
    public List<GlobalMessageResponse> receiveAllMessageGlobal();

}
