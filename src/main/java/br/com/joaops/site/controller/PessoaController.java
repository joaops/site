package br.com.joaops.site.controller;

import br.com.joaops.site.dto.PessoaDto;
import br.com.joaops.site.service.PessoaService;

import org.springframework.beans.factory.annotation.Autowired;
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
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("/pessoa/index");
        mav.addObject("pessoas", pessoaService.findAll());
        return mav;
    }
    
    @RequestMapping(value = "/cadastrar", method = RequestMethod.GET)
    public ModelAndView cadastrar() {
        ModelAndView mav = new ModelAndView("/pessoa/cadastrar");
        mav.addObject("pessoa", pessoaService.newDto());
        return mav;
    }
    
    @RequestMapping(value = "/salvar", method = RequestMethod.POST)
    public ModelAndView salvar(PessoaDto pessoa) {
        ModelAndView mav = new ModelAndView("redirect:/pessoa");
        pessoaService.save(pessoa);
        return mav;
    }
    
    @RequestMapping(value = "/{id}/mostrar", method = RequestMethod.GET)
    public ModelAndView mostrar(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("redirect:/pessoa");
        return mav;
    }
    
    @RequestMapping(value = "/{id}/alterar", method = RequestMethod.GET)
    public ModelAndView alterar(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("redirect:/pessoa");
        return mav;
    }
    
    @RequestMapping(value = "/{id}/deletar", method = RequestMethod.GET)
    public ModelAndView deletar(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("redirect:/pessoa");
        return mav;
    }
    
}