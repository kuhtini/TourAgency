package com.tour.model;

import com.tour.model.interfaces.ITour;

import javax.persistence.*;
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
}
