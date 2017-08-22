package br.com.joaops.site.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author João Paulo
 */
public class SystemUserDto implements Serializable {
    
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Boolean enabled;
    private Boolean locked;
    private Boolean accountCanExpire;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date accountExpiration;
    private Boolean credentialCanExpire;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date credentialExpiration;
    private List<SystemUserPermissionDto> systemUserPermission;
    
    public SystemUserDto() {
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.password = "";
        this.enabled = Boolean.TRUE;
        this.locked = Boolean.FALSE;
        this.accountCanExpire = Boolean.FALSE;
        this.credentialCanExpire = Boolean.FALSE;
        this.systemUserPermission = new ArrayList<>();
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Boolean getAccountCanExpire() {
        return accountCanExpire;
    }
    
    public void setAccountCanExpire(Boolean accountCanExpire) {
        this.accountCanExpire = accountCanExpire;
    }
    
    public Date getAccountExpiration() {
        return accountExpiration;
    }
    
    public void setAccountExpiration(Date accountExpiration) {
        this.accountExpiration = accountExpiration;
    }
    
    public Boolean getLocked() {
        return locked;
    }
    
    public void setLocked(Boolean locked) {
        this.locked = locked;
    }
    
    public Boolean getCredentialCanExpire() {
        return credentialCanExpire;
    }
    
    public void setCredentialCanExpire(Boolean credentialCanExpire) {
        this.credentialCanExpire = credentialCanExpire;
    }
    
    public Date getCredentialExpiration() {
        return credentialExpiration;
    }
    
    public void setCredentialExpiration(Date credentialExpiration) {
        this.credentialExpiration = credentialExpiration;
    }
    
    public Boolean getEnabled() {
        return enabled;
    }
    
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    
    public List<SystemUserPermissionDto> getSystemUserPermission() {
        return systemUserPermission;
    }
    
    public void setSystemUserPermission(List<SystemUserPermissionDto> systemUserPermission) {
        this.systemUserPermission = systemUserPermission;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Objects.hashCode(this.firstName);
        hash = 67 * hash + Objects.hashCode(this.lastName);
        hash = 67 * hash + Objects.hashCode(this.email);
        hash = 67 * hash + Objects.hashCode(this.password);
        hash = 67 * hash + Objects.hashCode(this.enabled);
        hash = 67 * hash + Objects.hashCode(this.locked);
        hash = 67 * hash + Objects.hashCode(this.accountCanExpire);
        hash = 67 * hash + Objects.hashCode(this.accountExpiration);
        hash = 67 * hash + Objects.hashCode(this.credentialCanExpire);
        hash = 67 * hash + Objects.hashCode(this.credentialExpiration);
        hash = 67 * hash + Objects.hashCode(this.systemUserPermission);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SystemUserDto other = (SystemUserDto) obj;
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.enabled, other.enabled)) {
            return false;
        }
        if (!Objects.equals(this.locked, other.locked)) {
            return false;
        }
        if (!Objects.equals(this.accountCanExpire, other.accountCanExpire)) {
            return false;
        }
        if (!Objects.equals(this.accountExpiration, other.accountExpiration)) {
            return false;
        }
        if (!Objects.equals(this.credentialCanExpire, other.credentialCanExpire)) {
            return false;
        }
        if (!Objects.equals(this.credentialExpiration, other.credentialExpiration)) {
            return false;
        }
        if (!Objects.equals(this.systemUserPermission, other.systemUserPermission)) {
            return false;
        }
        return true;
    }
    
}