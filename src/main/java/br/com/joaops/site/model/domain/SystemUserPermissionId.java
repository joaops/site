package br.com.joaops.site.model.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 *
 * @author João Paulo
 */
@Embeddable
public class SystemUserPermissionId implements Serializable {
    
    @ManyToOne(fetch = FetchType.EAGER)
    private SystemUser systemUser;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private SystemModule systemModule;
    
    public SystemUser getSystemUser() {
        return systemUser;
    }
    
    public void setSystemUser(SystemUser systemUser) {
        this.systemUser = systemUser;
    }
    
    public SystemModule getSystemModule() {
        return systemModule;
    }
    
    public void setSystemModule(SystemModule systemModule) {
        this.systemModule = systemModule;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
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
        final SystemUserPermissionId other = (SystemUserPermissionId) obj;
        if (!Objects.equals(this.systemUser, other.systemUser)) {
            return false;
        }
        if (!Objects.equals(this.systemModule, other.systemModule)) {
            return false;
        }
        return true;
    }
    
}