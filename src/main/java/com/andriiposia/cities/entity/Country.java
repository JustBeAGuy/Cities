package com.andriiposia.cities.entity;

import com.google.gson.annotations.Expose;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Администратор on 9/3/15.
 */
@Entity
@Table(name = "COUNTRY")
public class Country implements java.io.Serializable {

    @Id
    @Column(name = "COUNTRY_ID", unique = true, nullable = false)
    @GeneratedValue
    @Expose(serialize = false)
    private Integer countryId;

    @NotNull
    @Size(min=2, max = 32)
    @Column(name = "COUNTRY", unique = true, length = 32, nullable = false)
    private String country;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "country")
    @OneToMany(fetch = FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH} )
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE,
            org.hibernate.annotations.CascadeType.DELETE,
            org.hibernate.annotations.CascadeType.MERGE,
            org.hibernate.annotations.CascadeType.PERSIST,
            org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
    @JoinColumn(name = "COUNTRY_ID", nullable = false)
    private Set<City> cities = new HashSet<City>(0);

    public Country() {
    }

    public Country(String country, Set<City> cities) {
        this.country = country;
        cities = cities;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country.toLowerCase();
    }

    public Set<City> getCities() {
        return cities;
    }

    public void setCities(Set<City> cities) {
        cities = cities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Country country1 = (Country) o;

        if (!country.equals(country1.country)) return false;
        if (countryId != null ? !countryId.equals(country1.countryId) : country1.countryId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = countryId != null ? countryId.hashCode() : 0;
        result = 31 * result + country.hashCode();
        return result;
    }
}
