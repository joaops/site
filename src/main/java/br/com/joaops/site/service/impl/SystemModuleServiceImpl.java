package br.com.joaops.site.service.impl;

import br.com.joaops.site.dto.SystemModuleDto;
import br.com.joaops.site.model.dao.SystemModuleRepository;
import br.com.joaops.site.model.domain.SystemModule;
import br.com.joaops.site.service.SystemModuleService;
import java.util.ArrayList;
import java.util.List;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author João Paulo
 */
@Service("SystemModuleService")
public class SystemModuleServiceImpl implements SystemModuleService {
    
    @Autowired
    private SystemModuleRepository repository;
    
    @Autowired
    private Mapper mapper;
    
    @Override
    public SystemModuleDto newSystemModule() {
        return new SystemModuleDto();
    }
    
    @Override
    public SystemModuleDto save(SystemModuleDto moduleDto) {
        SystemModule module = new SystemModule();
        mapper.map(moduleDto, module);
        module = repository.save(module);
        SystemModuleDto novo = new SystemModuleDto();
        if (module != null) {
            mapper.map(module, novo);
        }
        return novo;
    }
    
    @Transactional(readOnly = true)
    @Override
    public SystemModuleDto findOne(Long id) {
        SystemModule module = repository.findOne(id);
        SystemModuleDto moduleDto = new SystemModuleDto();
        mapper.map(module, moduleDto);
        return moduleDto;
    }
    
    @Override
    public void delete(Long id) {
        repository.delete(id);
    }
    
    @Transactional(readOnly = true)
    @Override
    public Page<SystemModuleDto> searchAllModules(Pageable p) {
        List<SystemModuleDto> moduleDtos = new ArrayList<>();
        Page<SystemModule> modules = repository.findAll(p);
        for (SystemModule module : modules) {
            SystemModuleDto moduleDto = new SystemModuleDto();
            mapper.map(module, moduleDto);
            moduleDtos.add(moduleDto);
        }
        Page<SystemModuleDto> page = null;
        if (!moduleDtos.isEmpty()) {
            page = new PageImpl<>(moduleDtos, p, modules.getTotalElements());
        }
        return page;
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<SystemModuleDto> searchAllModules() {
        List<SystemModuleDto> listDto = new ArrayList<>();
        Iterable<SystemModule> list = repository.findAll();
        for (SystemModule module : list) {
            SystemModuleDto moduleDto = new SystemModuleDto();
            mapper.map(module, moduleDto);
            listDto.add(moduleDto);
        }
        return listDto;
    }
    
}