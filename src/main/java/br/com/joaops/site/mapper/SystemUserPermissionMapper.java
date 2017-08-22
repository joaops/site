package br.com.joaops.site.mapper;

import br.com.joaops.site.dto.SystemUserPermissionDto;
import br.com.joaops.site.model.domain.SystemUserPermission;
import org.dozer.loader.api.BeanMappingBuilder;

/**
 *
 * @author João Paulo
 */
public class SystemUserPermissionMapper extends BeanMappingBuilder {
    
    @Override
    protected void configure() {
        this.mapping(SystemUserPermission.class, SystemUserPermissionDto.class);
    }
    
}