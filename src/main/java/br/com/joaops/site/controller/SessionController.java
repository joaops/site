/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.joaops.site.controller;

import br.com.joaops.site.json.repository.SessionRepository;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
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
    private SimpMessagingTemplate simp;
    
    @RequestMapping(value = "session", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("session/index");
        String email = request.getRemoteUser();
        String sessionId = sessionRepository.getSessionIdByName(email);
        mav.addObject("sessionId", sessionId);
        return mav;
    }
    
    @RequestMapping(value = "session/painel-controle", method = RequestMethod.GET)
    public ModelAndView painel(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("session/painel");
        String email = request.getRemoteUser();
        String sessionId = sessionRepository.getSessionIdByName(email);
        mav.addObject("sessionId", sessionId);
        return mav;
    }
    
}