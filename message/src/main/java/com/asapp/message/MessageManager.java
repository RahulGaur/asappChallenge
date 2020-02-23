package com.asapp.message;

import java.util.ArrayList;
import java.util.Optional;

import com.asapp.MessageSvc.GetMessageRequest;
import com.asapp.MessageSvc.MessageDAO;
import com.asapp.MessageSvc.MessageResponse;
import com.asapp.MessageSvc.SendMessageRequest;
import com.asapp.MessageSvc.SendMessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@SpringBootApplication
@ComponentScan("com.asapp")
@EntityScan("com.asapp.message")
@Service
@Repository
public class MessageManager {

    MessageDAO messageDAO;

    @Autowired
    public MessageManager(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public MessageResponse sendMessage(SendMessageRequest sendMessageRequest) {
        Optional<MessageResponse> messageResponse = messageDAO.sendMessage(sendMessageRequest);

        return messageResponse.get();
    }

    public ArrayList<SendMessageResponse> getMessage(GetMessageRequest getMessageRequest) {
        ArrayList<SendMessageResponse> sendMessageResponse = messageDAO.getMessage(getMessageRequest);
        return sendMessageResponse;
    }
}
