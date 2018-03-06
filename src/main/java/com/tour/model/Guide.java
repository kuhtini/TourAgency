package com.tour.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tour.model.interfaces.IGuide;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue(value = "GUIDE")
public class Guide extends BaseUser implements IGuide {


    @Column(name = "end_visa_date")
    @Temporal(TemporalType.DATE)
    private Date endVisaDate;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Group> groups = new HashSet<>();


    public Date getEndVisaDate() {
        return endVisaDate;
    }

    public void setEndVisaDate(Date endVisaDate) {
        this.endVisaDate = endVisaDate;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public void leaveGroup(Group group){
        if (groups == null){
            throw new EntityExistsException("Еntity is not in  group");
        }
        groups.remove(group);

    }

    public void joinInToGroup(Group group){
        if (groups == null){
            groups = new HashSet<>();
        }
        groups.add(group);
    }

    public Guide() {
        super();
    }

    public Guide(String userName, String password, String firstName, String lastName, boolean active, String email, String confirmPassword, Set<Role> roles, Date endVisaDate, Set<Group> groups) {
        super(userName, password, firstName, lastName, active, email, confirmPassword, roles);
        this.endVisaDate = endVisaDate;
        this.groups = groups;
    }


}




