package br.com.joaops.site.controller;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author João Paulo
 */
@Controller
public class LoginController {
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("/login/index");
        return mav;
    }
    
    @RequestMapping(value = "/login/error", method = RequestMethod.GET)
    public ModelAndView loginError(Locale locale, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("login/index");
        mav.addObject("error", "error");
        return mav;
    }
    
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout() {
        ModelAndView mav = new ModelAndView("login/index");
        mav.addObject("logout", "logout");
        return mav;
    }
    
}