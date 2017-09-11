package br.com.joaops.site.config;

import br.com.joaops.site.security.AuthenticationProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

/**
 *
 * @author João Paulo
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    private final String defaultSecret = "ee3e40f590e8f63da4e811a462db4518245f34fb2a42278ed730b1ad19fc3cd21eaaf85b63156d62706860417c4180503cfa1501e12aad3381199d2faaacfcbd"; //Olá Mundo!!! em Criptografia Hash SHA-512
    
    @Autowired
    private AuthenticationProviderImpl authenticatorProvider;
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.eraseCredentials(true);
        auth.authenticationProvider(authenticatorProvider);
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/resources/**");
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").authenticated()
                .antMatchers("/system/user").hasRole("SYSTEM_USER_READ")
                .antMatchers("/system/user/show/*").hasRole("SYSTEM_USER_READ")
                .antMatchers("/system/user/add").hasRole("SYSTEM_USER_ADD")
                .antMatchers("/system/user/save").hasRole("SYSTEM_USER_ADD")
                .antMatchers("/system/user/edit/*").hasRole("SYSTEM_USER_EDIT")
                .antMatchers("/system/user/update").hasRole("SYSTEM_USER_EDIT")
                .antMatchers("/system/user/delete/*").hasRole("SYSTEM_USER_DELETE")
                .antMatchers("/pessoa").hasRole("SESSION_PESSOA_READ")
                .antMatchers("/pessoa/cadastrar").hasRole("SESSION_PESSOA_ADD")
                .antMatchers("/pessoa/salvar").hasRole("SESSION_PESSOA_ADD")
                .antMatchers("/pessoa/*/mostrar").hasRole("SESSION_PESSOA_READ")
                .antMatchers("/pessoa/*/alterar").hasRole("SESSION_PESSOA_EDIT")
                .antMatchers("/pessoa/*/deletar").hasRole("SESSION_PESSOA_DELETE")
                .antMatchers("/pessoa/deletar").hasRole("SESSION_PESSOA_DELETE")
                .antMatchers("/session").hasRole("SESSION_SESSION_READ")
                .antMatchers("/session/painel-controle").hasRole("SESSION_PESSOA_READ")
                .antMatchers("/session/ping").hasRole("SESSION_PESSOA_READ")
                .anyRequest().authenticated();
        
        //configuração adicional para a autenticação do cliente websocket
        http.httpBasic();
        
        http.headers().frameOptions().sameOrigin(); //configuração adicional para a autenticação do cliente websocket
        
        http.csrf().disable(); //pesquisar sobre csrf
        
        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login/check")
                .failureUrl("/login/error")
                .defaultSuccessUrl("/", true)
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll();
        
        http.sessionManagement()
                .maximumSessions(1).and()
                .sessionFixation()
                .newSession();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        StandardPasswordEncoder encoder = new StandardPasswordEncoder(defaultSecret);
        return encoder;
    }
    
}