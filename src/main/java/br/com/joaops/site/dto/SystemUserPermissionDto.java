package br.com.joaops.site.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author João Paulo
 */
public class SystemUserPermissionDto implements Serializable {
    
    private SystemUserPermissionIdDto systemUserPermissionId;
    private Boolean read;
    private Boolean add;
    private Boolean edit;
    private Boolean delete;
    
    public SystemUserPermissionIdDto getSystemUserPermissionId() {
        return systemUserPermissionId;
    }
    
    public void setSystemUserPermissionId(SystemUserPermissionIdDto systemUserPermissionId) {
        this.systemUserPermissionId = systemUserPermissionId;
    }
    
    public Boolean getRead() {
        return read;
    }
    
    public void setRead(Boolean read) {
        this.read = read;
    }
    
    public Boolean getAdd() {
        return add;
    }
    
    public void setAdd(Boolean add) {
        this.add = add;
    }
    
    public Boolean getEdit() {
        return edit;
    }
    
    public void setEdit(Boolean edit) {
        this.edit = edit;
    }
    
    public Boolean getDelete() {
        return delete;
    }
    
    public void setDelete(Boolean delete) {
        this.delete = delete;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(this.systemUserPermissionId);
        hash = 19 * hash + Objects.hashCode(this.read);
        hash = 19 * hash + Objects.hashCode(this.add);
        hash = 19 * hash + Objects.hashCode(this.edit);
        hash = 19 * hash + Objects.hashCode(this.delete);
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
        final SystemUserPermissionDto other = (SystemUserPermissionDto) obj;
        if (!Objects.equals(this.systemUserPermissionId, other.systemUserPermissionId)) {
            return false;
        }
        if (!Objects.equals(this.read, other.read)) {
            return false;
        }
        if (!Objects.equals(this.add, other.add)) {
            return false;
        }
        if (!Objects.equals(this.edit, other.edit)) {
            return false;
        }
        if (!Objects.equals(this.delete, other.delete)) {
            return false;
        }
        return true;
    }
    
}