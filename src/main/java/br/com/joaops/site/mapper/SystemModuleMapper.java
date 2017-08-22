package br.com.joaops.site.mapper;

import br.com.joaops.site.dto.SystemModuleDto;
import br.com.joaops.site.model.domain.SystemModule;
import org.dozer.loader.api.BeanMappingBuilder;

/**
 *
 * @author João Paulo
 */
public class SystemModuleMapper extends BeanMappingBuilder {
    
    @Override
    protected void configure() {
        this.mapping(SystemModule.class, SystemModuleDto.class);
    }
    
}