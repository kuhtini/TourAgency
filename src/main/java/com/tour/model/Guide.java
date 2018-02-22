package com.tour.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tour.model.interfaces.IGuide;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@DiscriminatorValue(value = "GUIDE")
public class Guide extends BaseUser implements IGuide {


    @Column(name = "end_visa_date")
    @Temporal(TemporalType.DATE)
    private Date endVisaDate;

    @JsonManagedReference
    @OneToMany(mappedBy = "guide", cascade = CascadeType.ALL)
    private Set<Group> groups;


    public Date getEndVisaDate() {
        return endVisaDate;
    }

    public void setEndVisaDate(Date endVisaDate) {
        this.endVisaDate = endVisaDate;
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




