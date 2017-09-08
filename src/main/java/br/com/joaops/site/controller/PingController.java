package br.com.joaops.site.controller;

import br.com.joaops.site.json.domain.PingJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

/**
 *
 * @author João Paulo
 */
@Controller
public class PingController {
    
    @Autowired
    private SimpMessagingTemplate simp;
    
    @Scheduled(fixedDelay = 60000)
    public void ping() {
        simp.convertAndSend("/topic/ping", new PingJson("ping")); // Envia um ping para todos inscritos em /topic
    }
    
}