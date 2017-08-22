package br.com.joaops.site.controller;

import br.com.joaops.site.dto.SystemUserDto;
import br.com.joaops.site.service.SystemModuleService;
import br.com.joaops.site.service.SystemUserService;
import br.com.joaops.site.util.PageWrapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author João Paulo
 */
@Controller
@RequestMapping(value = "system/user")
public class SystemUserController {
    
    @Autowired
    private SystemUserService systemUserService;
    
    @Autowired
    private SystemModuleService systemModuleService;
    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response, Pageable p) {
        ModelAndView mav = new ModelAndView("system/user/index");
        PageWrapper<SystemUserDto> page = new PageWrapper<>(systemUserService.searchAllUsers(p), "/system/user/");
        mav.addObject("page", page);
        return mav;
    }
    
    @RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
    public ModelAndView show(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("system/user/show");
        mav.addObject("user", systemUserService.findOne(id));
        return mav;
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add(HttpServletRequest request, HttpServletResponse response, Pageable p) {
        ModelAndView mav = new ModelAndView("system/user/add");
        mav.addObject("user", systemUserService.newSystemUser());
        mav.addObject("modules", systemModuleService.searchAllModules());
        return mav;
    }
    
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save(@Valid SystemUserDto user, BindingResult result) {
        ModelAndView mav;
        if (result.hasErrors()) {
            System.out.println("ERRO: "+result.toString());
            mav = new ModelAndView("/system/user/add");
            mav.addObject("org.springframework.validation.BindingResult.user", result);
            mav.addObject("user", user);
        } else {
            mav = new ModelAndView("redirect:/system/user");
            systemUserService.save(user);
        }
        return mav;
    }
    
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("system/user/edit");
        mav.addObject("user", systemUserService.findOne(id));
        mav.addObject("modules", systemModuleService.searchAllModules());
        return mav;
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(@Valid SystemUserDto user, BindingResult result) {
        ModelAndView mav;
        if (result.hasErrors()) {
            System.out.println("ERRO: "+result.toString());
            mav = new ModelAndView("/system/user/edit");
            mav.addObject("org.springframework.validation.BindingResult.user", result);
            mav.addObject("user", user);
        } else {
            mav = new ModelAndView("redirect:/system/user");
            systemUserService.save(user);
        }
        return mav;
    }
    
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("system/user/delete");
        mav.addObject("user", systemUserService.findOne(id));
        return mav;
    }
    
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView delete(@Valid SystemUserDto user) {
        ModelAndView mav = new ModelAndView("redirect:/system/user");
        systemUserService.delete(user);
        return mav;
    }
    
}