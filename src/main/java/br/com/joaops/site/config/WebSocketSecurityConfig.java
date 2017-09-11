/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.joaops.site.config;

import br.com.joaops.site.interceptor.PresenceChannelInterceptor;
import br.com.joaops.site.util.CONSTANTES;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 *
 * @author João Paulo Siqueira <joaopaulo1094@gmail.com>
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {
    
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/queue", "/topic");
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/user");
    }
    
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(CONSTANTES.ENDPOINTS.START).setAllowedOrigins("*").withSockJS();
        registry.addEndpoint(CONSTANTES.ENDPOINTS.MESSAGE).setAllowedOrigins("*").withSockJS();
    }
    
    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages
                .simpDestMatchers("/app/pessoaResponse").hasRole("SESSION_PESSOA_READ")
                .simpDestMatchers("/app*").authenticated()
                .simpSubscribeDestMatchers("/topic*").authenticated()
                .simpSubscribeDestMatchers("/queue*").authenticated()
                .nullDestMatcher().authenticated()
                .anyMessage().authenticated()
                .simpDestMatchers("/app/start").permitAll();
    }
    
    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
    
    @Bean
    public PresenceChannelInterceptor presenceChannelInterceptor() {
        return new PresenceChannelInterceptor();
    }
    
    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.setInterceptors(presenceChannelInterceptor());
    }
    
    @Override
    protected void customizeClientInboundChannel(ChannelRegistration registration) {
        registration.setInterceptors(presenceChannelInterceptor());
    }
    
}