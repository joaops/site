package br.com.joaops.site.controller;

import br.com.joaops.site.dto.PessoaDto;
import br.com.joaops.site.service.PessoaService;
import br.com.joaops.site.util.PageWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author João Paulo
 */
@Controller
@RequestMapping(value = "/pessoa")
public class PessoaController {
    
    @Autowired
    private PessoaService pessoaService;
    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(Pageable p) {
        ModelAndView mav = new ModelAndView("/pessoa/index");
        
        return mav;
    }
    
}