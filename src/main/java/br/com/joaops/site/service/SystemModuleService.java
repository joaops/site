/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.joaops.site.service;

import br.com.joaops.site.dto.SystemModuleDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author João Paulo
 */
public interface SystemModuleService {
    
    public SystemModuleDto newSystemModule();
    public SystemModuleDto save(SystemModuleDto moduleDto);
    public SystemModuleDto findOne(Long id);
    public void delete(Long id);
    public List<SystemModuleDto> searchAllModules();
    public Page<SystemModuleDto> searchAllModules(Pageable p);
    
}