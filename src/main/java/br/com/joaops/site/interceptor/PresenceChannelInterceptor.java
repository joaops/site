/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.joaops.site.interceptor;

import br.com.joaops.site.json.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import static org.springframework.messaging.simp.stomp.StompCommand.CONNECTED;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

/**
 *
 * @author João Paulo Siqueira <joaopaulo1094@gmail.com>
 */
@Component
public class PresenceChannelInterceptor extends ChannelInterceptorAdapter {
    
    @Autowired
    private SessionRepository repository;
    
    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(message);
        // Nós não nos preocupamos com mensagens não-STOMP como mensagens de heartbeat
        if(sha.getCommand() == null) {
            return;
        }
        String sessionId = sha.getSessionId();
        switch(sha.getCommand()) {
            case CONNECT:
                System.out.println("STOMP Connect [sessionId: " + sessionId + "]");
                String name = sha.getMessageHeaders().get("simpUser", UsernamePasswordAuthenticationToken.class).getName();
                System.out.println("STOMP Connect [name: " + name + "]");
                repository.add(name, sessionId);
                break;
            case CONNECTED:
                System.out.println("STOMP Connected [sessionId: " + sessionId + "]");
                break;
            case DISCONNECT:
                System.out.println("STOMP Disconnect [sessionId: " + sessionId + "]");
                String name2 = sha.getMessageHeaders().get("simpUser", UsernamePasswordAuthenticationToken.class).getName();
                System.out.println("STOMP Disconnect [name: " + name2 + "]");
                repository.remove(name2);
                break;
            case ERROR:
                System.out.println("STOMP Error [sessionId: " + sessionId + "]");
                break;
            default:
                System.out.println("Default");
                break;
        }
    }
    
}