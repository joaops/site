package br.com.joaops.site.mapper;

import br.com.joaops.site.dto.SystemUserDto;
import br.com.joaops.site.model.domain.SystemUser;
import org.dozer.loader.api.BeanMappingBuilder;

/**
 *
 * @author João Paulo
 */
public class SystemUserMapper extends BeanMappingBuilder {
    
    @Override
    protected void configure() {
        this.mapping(SystemUser.class, SystemUserDto.class);
    }
    
}