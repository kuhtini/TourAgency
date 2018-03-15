package com.tour.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tour.model.interfaces.ITour;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tour")
public class Tour implements ITour {


    public enum TourStatus {
        CANCELED,
        DELAYED,
        ACTIVE,
        COMPLETED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "from_date")
    // @Temporal(TemporalType.DATE)
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private Date fromDate;

    @Column(name = "by_date")
    // @Temporal(TemporalType.DATE)
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private Date byDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TourStatus status;

    @Column(name = "start_city")
    private String startCity;

    @ElementCollection
    private List<String> cities;

    @Column(name = "price")
    private int price;

    @Column(name = "description")
    private String description;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH}, mappedBy = "tour", orphanRemoval = true)
    private List<Group> groups = new ArrayList<Group>() {
    };



    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public Date getByDate() {
        return byDate;
    }

    public TourStatus getStatus() {
        return status;
    }

    public String getStartCity() {
        return startCity;
    }

    public List<String> getCities() {
        return cities;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public void setByDate(Date byDate) {
        this.byDate = byDate;
    }

    public void setStatus(TourStatus status) {
        this.status = status;
    }

    public void setStartCity(String startCity) {
        this.startCity = startCity;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Group> getGroups() {

        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public Tour(String name, Date fromDate, Date byDate, TourStatus status, String startCity, List<String> cities, int price) {
        this.name = name;
        this.fromDate = fromDate;
        this.byDate = byDate;
        this.status = status;
        this.startCity = startCity;
        this.cities = cities;
        this.price = price;
    }

    public Tour() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tour tour = (Tour) o;

        if (!name.equals(tour.name)) return false;
        if (!fromDate.equals(tour.fromDate)) return false;
        if (byDate != null ? !byDate.equals(tour.byDate) : tour.byDate != null) return false;
        return status == tour.status;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + fromDate.hashCode();
        result = 31 * result + (byDate != null ? byDate.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
