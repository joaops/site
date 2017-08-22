/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.joaops.site.model.dao;

import br.com.joaops.site.model.domain.SystemUserPermission;
import br.com.joaops.site.model.domain.SystemUserPermissionId;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author João Paulo
 */
public interface SystemUserPermissionRepository extends PagingAndSortingRepository<SystemUserPermission, SystemUserPermissionId> {
    
    @Query("SELECT p FROM SystemUserPermission p WHERE p.systemUserPermissionId.systemUser.id = :id")
    List<SystemUserPermission> findPermissionByUserId(@Param("id") Long id);
    
}