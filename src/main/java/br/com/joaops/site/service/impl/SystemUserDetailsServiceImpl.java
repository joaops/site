package br.com.joaops.site.service.impl;

import br.com.joaops.site.dto.SystemModuleDto;
import br.com.joaops.site.dto.SystemUserDto;
import br.com.joaops.site.dto.SystemUserPermissionDto;
import br.com.joaops.site.security.UserDetailsImpl;
import br.com.joaops.site.service.SystemUserService;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author João Paulo
 */
@Service("SystemUserDetailsService")
public class SystemUserDetailsServiceImpl implements UserDetailsService {
    
    @Autowired
    private MessageSource messageSource;
    
    @Autowired
    private SystemUserService systemUserService;
    
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = null;
        SystemUserDto userDto = systemUserService.findOneByEmail(username);
        if (userDto == null) {
            String message = "";
            try {
                message = messageSource.getMessage("DigestAuthenticationFilter.usernameNotFound", new Object[] {username}, LocaleContextHolder.getLocale());
            } catch (Exception e) {
                message = "Erro ao Conectar!!!";
            }
            UsernameNotFoundException ex = new UsernameNotFoundException(message);
            throw ex;
        } else {
            user = new UserDetailsImpl(userDto);
            Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) user.getAuthorities();
            Collection<SystemUserPermissionDto> permissions = userDto.getSystemUserPermission();
            for (SystemUserPermissionDto permission : permissions) {
                SystemModuleDto module = permission.getSystemUserPermissionId().getSystemModule();
                if (permission.getRead()) {
                    authorities.add(createRole(module, "READ"));
                }
                if (permission.getAdd()) {
                    authorities.add(createRole(module, "ADD"));
                }
                if (permission.getEdit()) {
                    authorities.add(createRole(module, "EDIT"));
                }
                if (permission.getDelete()) {
                    authorities.add(createRole(module, "DELETE"));
                }
            }
        }
        return user;
    }
    
    private SimpleGrantedAuthority createRole(SystemModuleDto module, String action) {
        StringBuilder sb = new StringBuilder("ROLE".concat("_"));
        sb.append(module.getCategory().concat("_"));
        sb.append(module.getName().concat("_"));
        sb.append(action);
        return new SimpleGrantedAuthority(sb.toString().trim().toUpperCase());
    }
    
}