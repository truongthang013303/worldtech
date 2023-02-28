package com.example.demo1.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Collection;
import java.util.LinkedHashSet;

@Entity
@Table(name = "privilege")
public class PrivilegeEntity extends BaseEntity{

    @Column(name = "name")
    private String name;
    @Column(name = "code")
    private String code;
    @ManyToMany(mappedBy = "privileges")
    private Collection<RoleEntity> roles=new LinkedHashSet<RoleEntity>();

    public String getName() {
        return name;
    }

    public Collection<RoleEntity> getRoles() {
        return roles;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setRoles(Collection<RoleEntity> roles) {
        this.roles = roles;
    }
}
