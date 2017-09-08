package br.com.joaops.site.controller;

import br.com.joaops.site.json.repository.SessionRepository;
import br.com.joaops.site.json.response.PessoaResponse;
import br.com.joaops.site.service.PessoaService;
import br.com.joaops.site.util.CONSTANTES;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author João Paulo
 */
@Controller
public class PessoaController {
    
    @Autowired
    private SessionRepository sessionRepository;
    
    @Autowired
    private PessoaService pessoaService;
    
    @MessageMapping(CONSTANTES.ENDPOINTS.PESSOA)
    public void funcionarioResponse(PessoaResponse response) {
        pessoaService.salvarPessoaResponse(response);
    }
    
    @RequestMapping(value = "/session/pessoa", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("/session/pessoa/index");
        String email = request.getRemoteUser();
        String sessionId = sessionRepository.getSessionIdByName(email);
        mav.addObject("sessionId", sessionId);
        mav.addObject("pessoas", pessoaService.findAll(sessionId));
        return mav;
    }
    
}