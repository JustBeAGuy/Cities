package com.andriiposia.cities.entity;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Администратор on 9/3/15.
 */
@Entity
@Table(name = "CITIES"
        , uniqueConstraints = @UniqueConstraint(
        columnNames = { "COUNTRY_ID", "CITY" }))
public class City implements java.io.Serializable {

    @Id
    @Column(name = "CITY_ID", unique = true, nullable = false)
    @GeneratedValue(strategy = IDENTITY)
    private Integer cityId;

    @NotNull
    @Size(min=1, max = 32)
    @Column(name = "CITY", length = 32, nullable = false)
    private String city;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "COUNTRY_ID", nullable = false, referencedColumnName = "COUNTRY_ID")
////    @Expose(serialize = false)
//    private Country country;

    public City() {
    }

    public City(String city) {
        this.city = city;
    }

//    public City(String city, Country country) {
//        this.city = city;
//        this.country = country;
//    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

//    public Country getCountry() {
//        return country;
//    }
//
//    public void setCountry(Country country) {
//        this.country = country;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City cityObject = (City) o;

        if (!city.equalsIgnoreCase(cityObject.city)) return false;
//        if (!cityId.equals(cityObject.cityId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = city.toLowerCase().hashCode();
        result = 31 * result + city.toLowerCase().hashCode();
        return result;
    }
}
