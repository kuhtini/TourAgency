package com.tour.model;

import com.tour.model.interfaces.ITour;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "from_date")
    @Temporal(TemporalType.DATE)
    private Date fromDate;

    @Column(name = "by_date")
    @Temporal(TemporalType.DATE)
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

    @OneToMany(fetch = FetchType.LAZY,cascade=CascadeType.ALL)
    private List<Group> groups;


    public TourStatus getTourStatus() {
        return status;
    }

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
}
