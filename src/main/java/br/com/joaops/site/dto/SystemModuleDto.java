package br.com.joaops.site.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author João Paulo
 */
public class SystemModuleDto implements Serializable {
    
    private Long id;
    private String name;
    private String category;
    private List<SystemUserPermissionDto> systemUserPermission;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public List<SystemUserPermissionDto> getSystemUserPermission() {
        return systemUserPermission;
    }
    
    public void setSystemUserPermission(List<SystemUserPermissionDto> systemUserPermission) {
        this.systemUserPermission = systemUserPermission;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
        hash = 59 * hash + Objects.hashCode(this.name);
        hash = 59 * hash + Objects.hashCode(this.category);
        hash = 59 * hash + Objects.hashCode(this.systemUserPermission);
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
        final SystemModuleDto other = (SystemModuleDto) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.category, other.category)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.systemUserPermission, other.systemUserPermission)) {
            return false;
        }
        return true;
    }
    
}