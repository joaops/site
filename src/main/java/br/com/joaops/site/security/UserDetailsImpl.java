package br.com.joaops.site.security;

import br.com.joaops.site.dto.SystemUserDto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author João Paulo
 */
public class UserDetailsImpl implements UserDetails, CredentialsContainer, Serializable {
    
    private final SystemUserDto user;
    private final List<GrantedAuthority> authorities;
    
    public UserDetailsImpl(SystemUserDto user) {
        this.user = user;
        this.authorities = new ArrayList<>();
        this.authorities.clear();
    }
    
    public SystemUserDto getUser() {
        return user;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }
    
    @Override
    public String getPassword() {
        return this.getUser().getPassword();
    }
    
    @Override
    public String getUsername() {
        return this.getUser().getEmail();
    }
    
    @Override
    public boolean isAccountNonExpired() {
        if (this.getUser().getAccountCanExpire()) {
            Calendar expirationDate = Calendar.getInstance();
            expirationDate.setTime(this.getUser().getAccountExpiration());
            if (Calendar.getInstance().after(expirationDate)) {
                return Boolean.FALSE;
            } else {
                return Boolean.TRUE;
            }
        } else {
            return Boolean.TRUE;
        }
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return !this.getUser().getLocked();
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        if (this.getUser().getCredentialCanExpire()) {
            Calendar expirationDate = Calendar.getInstance();
            expirationDate.setTime(this.getUser().getCredentialExpiration());
            if (Calendar.getInstance().after(expirationDate)) {
                return Boolean.FALSE;
            } else {
                return Boolean.TRUE;
            }
        } else {
            return Boolean.TRUE;
        }
    }
    
    @Override
    public boolean isEnabled() {
        return this.getUser().getEnabled();
    }
    
    @Override
    public void eraseCredentials() {
        this.getUser().setPassword(null);
    }
    
}