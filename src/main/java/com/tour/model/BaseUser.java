package com.tour.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tour.model.interfaces.IUser;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.core.style.ToStringCreator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;


@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type")
public class BaseUser implements IUser {

    public enum UserType {
        TOURIST,
        GUIDE
    }

    public enum Role{
        ROLE_USER,
        ROLE_STAFF,
        ROLE_ADMIN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Size(max = 50)
    @NotEmpty
   // @Pattern(regexp="^[\\pL '-]+$")
    @Column(name = "user_name", unique = true)
    private String userName;

    //@Size(min = 6, max = 50)
    //@Pattern(regexp="^[a-zA-Z0-9]+$")
    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "phone")
    private String phone;




    @Column(unique = true)
    private String email;

    @Transient
    private String confirmPassword;

    @JsonManagedReference
    @ElementCollection
    private Set<BaseUser.Role> roles;

    public BaseUser(String userName, String password, String firstName, String lastName, boolean active, String email, String confirmPassword, Set<BaseUser.Role> roles) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.active = active;
        this.email = email;
        this.confirmPassword = confirmPassword;
        this.roles = roles;
    }

    public BaseUser() {
    }

    //GetSet
    public long getId () {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Set<BaseUser.Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {

        this.roles = roles;

    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    //EndGetSet



    public String toString() {
        return new ToStringCreator(this)
                .append("id", this.getId())
                .append("first_name", this.getFirstName())
                .append("last_name", this.getLastName())
                .append("user_name", this.getUserName())
                .append("email", this.getEmail()).toString();

    }
}
