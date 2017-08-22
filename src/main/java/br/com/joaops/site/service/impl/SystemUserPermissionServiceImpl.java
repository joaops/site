package br.com.joaops.site.service.impl;

import br.com.joaops.site.dto.SystemUserPermissionDto;
import br.com.joaops.site.model.dao.SystemModuleRepository;
import br.com.joaops.site.model.dao.SystemUserPermissionRepository;
import br.com.joaops.site.model.dao.SystemUserRepository;
import br.com.joaops.site.model.domain.SystemUserPermission;
import br.com.joaops.site.model.domain.SystemUserPermissionId;
import br.com.joaops.site.service.SystemUserPermissionService;
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
@Service("SystemUserPermissionService")
public class SystemUserPermissionServiceImpl implements SystemUserPermissionService {
    
    @Autowired
    private SystemUserPermissionRepository repository;
    
    @Autowired
    private SystemUserRepository userRepository;
    
    @Autowired
    private SystemModuleRepository moduleRepository;
    
    @Autowired
    private Mapper mapper;
    
    @Override
    public SystemUserPermissionDto newSystemUserPermission() {
        return new SystemUserPermissionDto();
    }
    
    @Override
    public SystemUserPermissionDto save(SystemUserPermissionDto permissionDto) {
        SystemUserPermission permission = new SystemUserPermission();
        
        SystemUserPermissionId permissionId = new SystemUserPermissionId();
        permissionId.setSystemUser(userRepository.findOne(permissionDto.getSystemUserPermissionId().getSystemUser().getId()));
        permissionId.setSystemModule(moduleRepository.findOne(permissionDto.getSystemUserPermissionId().getSystemModule().getId()));
        
        permission.setSystemUserPermissionId(permissionId);
        permission.setRead(permissionDto.getRead());
        permission.setAdd(permissionDto.getAdd());
        permission.setEdit(permissionDto.getEdit());
        permission.setDelete(permissionDto.getDelete());
        
        permission = repository.save(permission);
        SystemUserPermissionDto novo = new SystemUserPermissionDto();
        if (permission != null) {
            mapper.map(permission, novo);
        }
        return novo;
    }
    
    @Override
    public void delete(Long idUser, Long idModule) {
        SystemUserPermissionId permissionId = new SystemUserPermissionId();
        permissionId.setSystemUser(userRepository.findOne(idUser));
        permissionId.setSystemModule(moduleRepository.findOne(idModule));
        repository.delete(permissionId);
    }
    
    @Override
    public void deleteAllByUser(Long idUser) {
        List<SystemUserPermission> permissions = repository.findPermissionByUserId(idUser);
        if (permissions != null) {
            repository.delete(permissions);
        }
    }
    
    @Transactional(readOnly = true)
    @Override
    public SystemUserPermissionDto findOne(Long idUser, Long idModule) {
        SystemUserPermission permission = null;
        SystemUserPermissionId permissionId = new SystemUserPermissionId();
        permissionId.setSystemUser(userRepository.findOne(idUser));
        permissionId.setSystemModule(moduleRepository.findOne(idModule));
        permission = repository.findOne(permissionId);
        SystemUserPermissionDto permissionDto = new SystemUserPermissionDto();
        mapper.map(permission, permissionDto);
        return permissionDto;
    }
    
    @Transactional(readOnly = true)
    @Override
    public Page<SystemUserPermissionDto> searchAllUsersPermissions(Pageable p) {
        List<SystemUserPermissionDto> permissionDtos = new ArrayList<>();
        Page<SystemUserPermission> permissions = repository.findAll(p);
        for (SystemUserPermission permission : permissions) {
            SystemUserPermissionDto permissionDto = new SystemUserPermissionDto();
            mapper.map(permission, permissionDto);
            permissionDtos.add(permissionDto);
        }
        Page<SystemUserPermissionDto> page = null;
        if (!permissionDtos.isEmpty()) {
            page = new PageImpl<>(permissionDtos, p, permissions.getTotalElements());
        }
        return page;
    }
    
}