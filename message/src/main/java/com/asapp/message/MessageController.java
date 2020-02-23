package com.asapp.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.asapp.MessageSvc.ErrorResponse;
import com.asapp.MessageSvc.GetMessageRequest;
import com.asapp.MessageSvc.MessageResponse;
import com.asapp.MessageSvc.SendMessageRequest;
import com.asapp.MessageSvc.SendMessageResponse;
import com.asapp.content.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;

@RestController
public class MessageController {

    private static final String IS_AUTH_TOKEN_VALID_URL = "http://auth-service/checkAuthToken/{authToken}";

    @Autowired
    RestTemplate restTemplate;
    MessageManager messageManager;

    @Autowired
    public MessageController(MessageManager messageManager){
        this.messageManager = messageManager;
    }

    @PostMapping("/messages")
    public MessageResponse sendMessage(@RequestBody SendMessageRequest sendMessageRequest, @RequestHeader("Authorization") String authToken) {
        String sender = sendMessageRequest.getSender();
        try{
            boolean isValidToken = restTemplate.exchange(IS_AUTH_TOKEN_VALID_URL,
                HttpMethod.GET, null, new ParameterizedTypeReference<Boolean>() {}, authToken).getBody();
            if(isValidToken){
                MessageResponse messageResponse = messageManager.sendMessage(sendMessageRequest);
                return messageResponse;
            }
        } catch (UnknownHttpStatusCodeException e){
            MessageResponse response = new MessageResponse();
            response.setErrorResponse(ErrorResponse.INCORRECT_REQUEST);
            return response;
        }
        return new MessageResponse();
    }

    @RequestMapping(value = "/messages", method = { RequestMethod.GET })
    public HashMap<String,ArrayList<SendMessageResponse>> getMessages(int recipient, int start, Optional<Integer> limit, @RequestHeader("Authorization") String authToken) {
        ArrayList<SendMessageResponse> sendMessageResponses;
        HashMap<String, ArrayList<SendMessageResponse>> response = new HashMap<>();
        response.put("messages",new ArrayList<>());
        try{
            boolean isValidToken = restTemplate.exchange(IS_AUTH_TOKEN_VALID_URL,
                HttpMethod.GET, null, new ParameterizedTypeReference<Boolean>() {}, authToken).getBody();
            if (isValidToken){
                int l = limit.isPresent()?limit.get():100;
                GetMessageRequest getMessageRequest = new GetMessageRequest(String.valueOf(recipient),start,l);
                sendMessageResponses =  messageManager.getMessage(getMessageRequest);
                response.put("messages",sendMessageResponses);
                return response;
            }
        } catch (Exception e) {
            return response;
        }
        return response;
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
