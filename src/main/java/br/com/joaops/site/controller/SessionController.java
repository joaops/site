/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.joaops.site.controller;

import br.com.joaops.site.json.domain.PingJson;
import br.com.joaops.site.json.protocol.Message;
import br.com.joaops.site.json.repository.MessageRepository;
import br.com.joaops.site.json.repository.SessionRepository;
import br.com.joaops.site.util.CONSTANTES;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author João Paulo Siqueira <joaopaulo1094@gmail.com>
 */
@Controller
public class SessionController {
    
    @Autowired
    private SessionRepository sessionRepository;
    
    @Autowired
    private MessageRepository messageRepository;
    
    @Autowired
    private SimpMessagingTemplate simp;
    
    @MessageMapping(CONSTANTES.ENDPOINTS.MESSAGE)
    public void message(Message message) {
        messageRepository.save(message);
    }
    
    @RequestMapping(value = "/session", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("session/index");
        String email = request.getRemoteUser();
        String sessionId = sessionRepository.getSessionIdByName(email);
        mav.addObject("sessionId", sessionId);
        return mav;
    }
    
    @RequestMapping(value = "/session/ping", method = RequestMethod.GET)
    public ModelAndView ping(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("redirect:/session");
        String email = request.getRemoteUser();
        String sessionId = sessionRepository.getSessionIdByName(email);
        PingJson ping = new PingJson("Ping Para a Sessão " + sessionId);
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        simp.convertAndSendToUser(sessionId, CONSTANTES.QUEUES.PING, ping, headerAccessor.getMessageHeaders());
        return mav;
    }
    
    @RequestMapping(value = "/session/painel-controle", method = RequestMethod.GET)
    public ModelAndView painel(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("session/painel");
        String email = request.getRemoteUser();
        String sessionId = sessionRepository.getSessionIdByName(email);
        mav.addObject("sessionId", sessionId);
        return mav;
    }
    
}