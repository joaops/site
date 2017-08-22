/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.joaops.site.model.dao;

import br.com.joaops.site.model.domain.SystemUser;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author João Paulo
 */
public interface SystemUserRepository extends PagingAndSortingRepository<SystemUser, Long> {
    
    public SystemUser findOneByEmail(String email);
    
}