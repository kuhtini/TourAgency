package com.tour.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tour.model.interfaces.IGroup;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "group_tour")
public class Group implements IGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @ManyToOne(targetEntity = Guide.class)
    private Guide guide;

    @ManyToOne
    private Tour tour;

    @JoinTable(
            name = "group_user",
            joinColumns = @JoinColumn(
                    name = "group_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "users_id",
                    referencedColumnName = "id"
            )
    )
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonManagedReference
    private Set<BaseUser> tourists = new HashSet<>();


    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public Guide getGuide() {
        return guide;
    }

    public void setGuide(Guide guide) {
        this.guide = guide;
    }

    @Override
    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    @Override
    public Set<BaseUser> getTourists() {
        return tourists;
    }

    public void setTourists(Set<BaseUser> tourists) {
        this.tourists = tourists;
    }

    public void addTourist(BaseUser tourist) {
        if (tourists == null) {
            tourists = new HashSet<>();
        }
        tourists.add(tourist);
    }

    public Group(Guide guide, Tour tour, Set<BaseUser> tourists) {
        this.guide = guide;
        this.tour = tour;
        this.tourists = tourists;
    }

    public Group() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        if (guide != null ? !guide.equals(group.guide) : group.guide != null) return false;
        return tour != null ? tour.equals(group.tour) : group.tour == null;
    }

    @Override
    public int hashCode() {
        int result = guide != null ? guide.hashCode() : 0;
        result = 31 * result + (tour != null ? tour.hashCode() : 0);
        return result;
    }
}
