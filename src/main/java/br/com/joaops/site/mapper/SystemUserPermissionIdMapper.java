package br.com.joaops.site.mapper;

import br.com.joaops.site.dto.SystemUserPermissionIdDto;
import br.com.joaops.site.model.domain.SystemUserPermissionId;
import org.dozer.loader.api.BeanMappingBuilder;

/**
 *
 * @author João Paulo
 */
public class SystemUserPermissionIdMapper extends BeanMappingBuilder {
    
    @Override
    protected void configure() {
        this.mapping(SystemUserPermissionId.class, SystemUserPermissionIdDto.class);
    }
    
}