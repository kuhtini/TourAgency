package com.tour.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tour.model.interfaces.IGuideUser;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@DiscriminatorValue(value = "GUIDE")
public class Guide extends BaseUser implements IGuideUser {


    @Column(name = "end_visa_date")
    @Temporal(TemporalType.DATE)
    private Date endVisaDate;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL)
    private List<Group> groups = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "group_user",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private List<Group> groupsLikeTourist = new ArrayList<>();

    public Date getEndVisaDate() {
        return endVisaDate;
    }

    public void setEndVisaDate(Date endVisaDate) {
        this.endVisaDate = endVisaDate;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public void leaveGroup(Group group) {
        if (!groups.contains(group)) {
            throw new EntityExistsException("Еntity is not in  group");
        }
        groups.remove(group);

    }

    public void joinInToGroup(Group group) {
        if (groups == null) {
            groups = new ArrayList<>();
        }
        groups.add(group);
    }

    public void joinInToGroupAsTourist(Group group) {
        if (groupsLikeTourist == null) {
            groupsLikeTourist = new ArrayList<>();
        }
        groupsLikeTourist.add(group);
    }

    public void leaveGroupAsTourist(Group group) {
        if (!groupsLikeTourist.contains(group)) {
            throw new EntityExistsException("Еntity is not in  group");
        }
        groupsLikeTourist.remove(group);
    }

    public Guide() {
        super();
    }

    public Guide(String userName, String password, String firstName, String lastName, boolean active, String email, String confirmPassword, Set<Role> roles, Date endVisaDate, List<Group> groups) {
        super(userName, password, firstName, lastName, active, email, confirmPassword, roles);
        this.endVisaDate = endVisaDate;
        this.groups = groups;
    }

    public List<Group> getGroupsLikeTourist() {
        return groupsLikeTourist;
    }

    public void setGroupsLikeTourist(List<Group> groupsLikeTourist) {
        this.groupsLikeTourist = groupsLikeTourist;
    }
}




