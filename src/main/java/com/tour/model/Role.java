package com.tour.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tour.model.enums.UserRole;

import javax.persistence.*;
import java.util.Set;

/**
 * Simple JavaBean object that represents role of {@link BaseUser}.
 *
 * @author Eugene Suleimanov
 * @version 1.0
 */

//@Entity
//@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private UserRole name;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER,cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Set<BaseUser> baseUsers;

    public Role() {
    }

    public Role(UserRole name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserRole getName() {
        return name;
    }

    public void setName(UserRole name) {
        this.name = name;
    }

    public Set<BaseUser> getBaseUsers() {
        return baseUsers;
    }

    public void setBaseUsers(Set<BaseUser> baseUsers) {
        this.baseUsers = baseUsers;
    }

    @Override
    public String toString() {
        return "IRole{" +
                "id=" + id +
                ", name='" + name.toString() + '\'' +
                ", baseUsers=" + baseUsers +
                '}';
    }
}
