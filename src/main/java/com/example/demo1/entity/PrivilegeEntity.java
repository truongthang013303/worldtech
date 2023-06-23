package com.example.demo1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
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
    @JsonIgnore
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
