package br.com.joaops.site.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *
 * @author João Paulo
 */
@Configuration
@EnableAsync
@EnableScheduling
@ComponentScan(basePackages={"br.com.joaops.site"}, 
        excludeFilters=@ComponentScan.Filter(
                type=FilterType.REGEX, 
                pattern={"br.com.joaops.site.controller.*"}))
public class ApplicationConfig {
    
}