package br.com.joaops.site.config;

import java.util.List;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

/**
 *
 * @author João Paulo
 */
@EnableWebMvc
@Configuration
@ComponentScan("br.com.joaops.site.controller")
public class WebConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {
    
    private static final int MAX_PAGE_SIZE = 10;
    
    private ApplicationContext applicationContext;
    
    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        this.applicationContext = ac;
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/WEB-INF/resources/");
    }
    
    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine;
        templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(this.templateResolver());
        templateEngine.addDialect(springSecurityDialect());
        return templateEngine;
    }
    
    private ITemplateResolver templateResolver() {
        SpringResourceTemplateResolver resolver;
        resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext(this.applicationContext);
        resolver.setPrefix("/WEB-INF/templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding("UTF-8");
        return resolver;
    }
    
    private SpringSecurityDialect springSecurityDialect() {
        SpringSecurityDialect dialect;
        dialect = new SpringSecurityDialect();
        return dialect;
    }
    
    @Bean
    public ViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver;
        viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(this.templateEngine());
        viewResolver.setCharacterEncoding("UTF-8");
        viewResolver.setOrder(1);
        return viewResolver;
    }
    
    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource());
        return validator;
    }
    
    private MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setBasenames(
                "classpath:/org/springframework/security/messages",
                "classpath:/org/hibernate/validator/ValidationMessages",
                "classpath:/br/com/joaops/site/i18n/messages");
        messageSource.setCacheSeconds(0); //Escolher um valor do cache na hora da implantação. Para testes deixar como '0' zero
        messageSource.setFallbackToSystemLocale(Boolean.TRUE);
        return messageSource;
    }
    
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
        resolver.setMaxPageSize(WebConfig.MAX_PAGE_SIZE);
        argumentResolvers.add(resolver);
    }
    
}