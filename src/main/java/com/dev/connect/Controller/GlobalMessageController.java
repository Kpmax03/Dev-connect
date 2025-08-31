package com.dev.connect.Controller;

import com.dev.connect.RequestDto.GlobalMessageRequest;
import com.dev.connect.ResponseDto.GlobalMessageResponse;
import com.dev.connect.repository.GlobalMessageRepository;
import com.dev.connect.service.GlobalMessageService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController@Slf4j
@RequestMapping("/global")
public class GlobalMessageController {

    @Autowired
    private GlobalMessageService globalMessageService;

    @PostMapping("/send")
    public ResponseEntity<GlobalMessageResponse> sendMessageGlobal(@RequestBody GlobalMessageRequest globalMessageRequest, Principal principal){
      return ResponseEntity.ok(globalMessageService.sendMessageGlobaly(globalMessageRequest,principal));
    }
    @GetMapping("/receive")
    public ResponseEntity<List<GlobalMessageResponse>> recieveGlobalMessage(){
        return ResponseEntity.ok(globalMessageService.receiveAllMessageGlobal());
    }
}
