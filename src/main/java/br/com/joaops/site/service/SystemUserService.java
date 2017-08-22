/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.joaops.site.service;

import br.com.joaops.site.dto.SystemUserDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author João Paulo
 */
public interface SystemUserService {
    
    public SystemUserDto newSystemUser();
    public SystemUserDto save(SystemUserDto userDto);
    public SystemUserDto findOne(Long id);
    public SystemUserDto findOneByEmail(String email);
    public void delete(Long id);
    public void delete(SystemUserDto userDto);
    public List<SystemUserDto> searchAllUsers();
    public Page<SystemUserDto> searchAllUsers(Pageable p);
    
}