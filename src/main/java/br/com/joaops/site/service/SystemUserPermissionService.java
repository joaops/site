/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.joaops.site.service;

import br.com.joaops.site.dto.SystemUserPermissionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author João Paulo
 */
public interface SystemUserPermissionService {
    
    public SystemUserPermissionDto newSystemUserPermission();
    public SystemUserPermissionDto save(SystemUserPermissionDto systemUserPermissionDto);
    public void delete(Long idUser, Long idModule);
    public void deleteAllByUser(Long idUser);
    public SystemUserPermissionDto findOne(Long idUser, Long idModule);
    public Page<SystemUserPermissionDto> searchAllUsersPermissions(Pageable p);
    
}
