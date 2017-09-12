package br.com.joaops.site.controller;

import br.com.joaops.site.dto.PessoaDto;
import br.com.joaops.site.service.PessoaService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
    private PessoaService pessoaService;
    
    @RequestMapping(value = "/pessoa", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("/pessoa/index");
        try {
            mav.addObject("pessoas", pessoaService.findAll());
        } catch (Exception e) {
            mav.addObject("erro", e.getMessage());
        }
        return mav;
    }
    
    @RequestMapping(value = "/pessoa/cadastrar", method = RequestMethod.GET)
    public ModelAndView cadastrar(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("/pessoa/cadastrar");
        mav.addObject("tituloPanel", "Cadastrar Uma Nova Pessoa");
        mav.addObject("pessoa", pessoaService.newDto());
        return mav;
    }
    
    @RequestMapping(value = "/pessoa/{id}/mostrar", method = RequestMethod.GET)
    public ModelAndView mostrar(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) {
        ModelAndView mav = new ModelAndView("/pessoa/mostrar");
        try {
            mav.addObject("pessoa", pessoaService.findOne(id));
        } catch (Exception e) {
            mav.addObject("erro", e.getMessage());
        }
        return mav;
    }
    
    @RequestMapping(value = "/pessoa/{id}/alterar", method = RequestMethod.GET)
    public ModelAndView alterar(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) {
        ModelAndView mav = new ModelAndView("/pessoa/cadastrar");
        mav.addObject("tituloPanel", "Alterar Dados da Pessoa");
        try {
            mav.addObject("pessoa", pessoaService.findOne(id));
        } catch (Exception e) {
            mav.addObject("erro", e.getMessage());
        }
        return mav;
    }
    
    @RequestMapping(value = "/pessoa/{id}/deletar", method = RequestMethod.GET)
    public ModelAndView deletar(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) {
        ModelAndView mav = new ModelAndView("/pessoa/deletar");
        try {
            mav.addObject("pessoa", pessoaService.findOne(id));
        } catch (Exception e) {
            mav.addObject("erro", e.getMessage());
        }
        return mav;
    }
    
    @RequestMapping(value = "/pessoa/salvar", method = RequestMethod.POST)
    public ModelAndView salvar(HttpServletRequest request, HttpServletResponse response, TimeZone timezone, @ModelAttribute("pessoa") @Valid PessoaDto pessoa, BindingResult result) {
        ModelAndView mav;
        if (result.hasErrors()) {
            mav = new ModelAndView("/pessoa/cadastrar");
            mav.addObject("pessoa", pessoa);
            if (pessoa.getId() == 0) {
                mav.addObject("tituloPanel", "Cadastrar Uma Nova Pessoa");
            } else {
                mav.addObject("tituloPanel", "Alterar Dados da Pessoa");
            }
        } else {
            try {
                System.out.println("Data: " + pessoa.getNascimento().toString());
                System.out.println("TimeZone: " + timezone.getDisplayName());
                // Arruma o TimeZone
                SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd");
                isoFormat.setTimeZone(timezone);
                Date date = isoFormat.parse(isoFormat.format(pessoa.getNascimento()));
                pessoa.setNascimento(date);
                pessoaService.save(pessoa);
                mav = new ModelAndView("redirect:/pessoa");
            } catch (Exception e) {
                mav = new ModelAndView("/pessoa/cadastrar");
                mav.addObject("pessoa", pessoa);
                mav.addObject("erro", e.getMessage());
                if (pessoa.getId() == 0) {
                    mav.addObject("tituloPanel", "Cadastrar Uma Nova Pessoa");
                } else {
                    mav.addObject("tituloPanel", "Alterar Dados da Pessoa");
                }
            }
        }
        return mav;
    }
    
    @RequestMapping(value = "/pessoa/deletar", method = RequestMethod.POST)
    public ModelAndView deletar(HttpServletRequest request, HttpServletResponse response, PessoaDto pessoa) {
        ModelAndView mav;
        try {
            pessoaService.delete(pessoa.getId());
            mav = new ModelAndView("redirect:/pessoa");
        } catch (Exception e) {
            mav = new ModelAndView("/pessoa/deletar");
            mav.addObject("erro", e.getMessage());
        }
        return mav;
    }
    
}