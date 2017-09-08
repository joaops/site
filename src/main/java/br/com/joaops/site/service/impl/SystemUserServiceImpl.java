package br.com.joaops.site.service.impl;

import br.com.joaops.site.dto.SystemUserDto;
import br.com.joaops.site.dto.SystemUserPermissionDto;
import br.com.joaops.site.model.dao.SystemUserRepository;
import br.com.joaops.site.model.domain.SystemUser;
import br.com.joaops.site.service.SystemUserPermissionService;
import br.com.joaops.site.service.SystemUserService;
import java.util.ArrayList;
import java.util.List;
import org.dozer.Mapper;
import org.dozer.MappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author João Paulo
 */
@Service("SystemUserService")
public class SystemUserServiceImpl implements SystemUserService {
    
    @Autowired
    private SystemUserRepository userRepository;
    
    @Autowired
    private SystemUserPermissionService permissionService;
    
    @Autowired
    private PasswordEncoder encoder;
    
    @Autowired
    private Mapper mapper;
    
    @Override
    public SystemUserDto newSystemUser() {
        return new SystemUserDto();
    }
    
    @Override
    public SystemUserDto save(SystemUserDto userDto) {
        boolean existe = Boolean.FALSE;
        if (userDto.getId() != null) {
            existe = userRepository.exists(userDto.getId());
        }
        if (existe) {
            permissionService.deleteAllByUser(userDto.getId());
            for (SystemUserPermissionDto permission : userDto.getSystemUserPermission()) {
                permission.getSystemUserPermissionId().setSystemUser(userDto);
                permissionService.save(permission);
            }
        }
        SystemUser user = new SystemUser();
        mapper.map(userDto, user);
        user.setPassword(encoder.encode(user.getPassword()));
        user = userRepository.save(user);
        SystemUserDto salvo = null;
        if (user != null) {
            salvo = new SystemUserDto();
            mapper.map(user, salvo);
            if (!existe) {
                for (SystemUserPermissionDto permission : userDto.getSystemUserPermission()) {
                    permission.getSystemUserPermissionId().setSystemUser(salvo);
                    permissionService.save(permission);
                }
            }
        }
        return salvo;
    }
    
    @Transactional(readOnly = true)
    @Override
    public SystemUserDto findOne(Long id) {
        SystemUser user = userRepository.findOne(id);
        SystemUserDto userDto = null;
        if (user != null) {
            userDto = new SystemUserDto();
            mapper.map(user, userDto);
        }
        return userDto;
    }
    
    @Transactional(readOnly = true)
    @Override
    public SystemUserDto findOneByEmail(String email) {
        SystemUser user = userRepository.findOneByEmail(email);
        SystemUserDto userDto = null;
        if (user != null) {
            userDto = new SystemUserDto();
            mapper.map(user, userDto);
        }
        return userDto;
    }
    
    @Override
    public void delete(Long id) {
        try {
            userRepository.delete(id);
        } catch (Exception e) {
        }
    }
    
    @Override
    public void delete(SystemUserDto userDto) {
        try {
            SystemUser user = new SystemUser();
            mapper.map(userDto, user);
            userRepository.delete(user);
        } catch (MappingException e) {
        }
    }
    
    @Override
    public List<SystemUserDto> searchAllUsers() {
        List<SystemUserDto> listDto = new ArrayList<>();
        Iterable<SystemUser> list = userRepository.findAll();
        for (SystemUser user : list) {
            SystemUserDto userDto = new SystemUserDto();
            mapper.map(user, userDto);
            listDto.add(userDto);
        }
        return listDto;
    }
    
    @Transactional(readOnly = true)
    @Override
    public Page<SystemUserDto> searchAllUsers(Pageable p) {
        List<SystemUserDto> listDto = new ArrayList<>();
        Page<SystemUser> list = userRepository.findAll(p);
        for (SystemUser user : list) {
            SystemUserDto userDto = new SystemUserDto();
            mapper.map(user, userDto);
            listDto.add(userDto);
        }
        Page<SystemUserDto> page = null;
        if (!listDto.isEmpty()) {
            page = new PageImpl<>(listDto, p, list.getTotalElements());
        } else {
            page = new PageImpl<>(new ArrayList<SystemUserDto>(), p, 0);
        }
        return page;
    }
    
}