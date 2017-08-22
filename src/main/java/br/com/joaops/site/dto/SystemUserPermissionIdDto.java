package br.com.joaops.site.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author João Paulo
 */
public class SystemUserPermissionIdDto implements Serializable {
    
    private SystemUserDto systemUser;
    private SystemModuleDto systemModule;
    
    public SystemUserDto getSystemUser() {
        return systemUser;
    }
    
    public void setSystemUser(SystemUserDto systemUser) {
        this.systemUser = systemUser;
    }
    
    public SystemModuleDto getSystemModule() {
        return systemModule;
    }
    
    public void setSystemModule(SystemModuleDto systemModule) {
        this.systemModule = systemModule;
    }
    
    //equals e hashcode?
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.systemUser);
        hash = 17 * hash + Objects.hashCode(this.systemModule);
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
        final SystemUserPermissionIdDto other = (SystemUserPermissionIdDto) obj;
        if (!Objects.equals(this.systemUser, other.systemUser)) {
            return false;
        }
        if (!Objects.equals(this.systemModule, other.systemModule)) {
            return false;
        }
        return true;
    }
    
}