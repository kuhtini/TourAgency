package com.tour.model;

import com.tour.model.interfaces.ITouristUser;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Entity
@DiscriminatorValue(value = "TOURIST")
public class Tourist extends BaseUser implements ITouristUser {


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "group_user",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private List<Group> groups = new ArrayList<>();

    public Tourist(String userName, String password, String firstName, String lastName, boolean active, String email, String confirmPassword, Set<Role> roles, List<Group> groups) {
        super(userName, password, firstName, lastName, active, email, confirmPassword, roles);
        this.groups = groups;
    }

    public Tourist() {
        super();
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void joinInToGroup(Group group) {
        if (groups == null) {
            groups = new ArrayList<>();

        }
        groups.add(group);
    }

    public void leaveGroup(Group group) {
        if (groups == null) {
            throw new EntityExistsException("Еntity is not in the ");
        }
        groups.remove(group);

    }


    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }


}
